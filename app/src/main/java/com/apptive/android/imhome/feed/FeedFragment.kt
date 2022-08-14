package com.apptive.android.imhome.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseFragment
import com.apptive.android.imhome.baseClass.RecyclerViewDecoration
import com.apptive.android.imhome.databinding.FragmentFeedBinding
import com.apptive.android.imhome.signup.NicknameInteractor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class FeedFragment:BaseFragment() {

    private lateinit var callback: OnBackPressedCallback
    private val auth: FirebaseAuth = Firebase.auth
    private val currentUser=auth.currentUser

    private lateinit var binding: FragmentFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFeedBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nickNameInteractor=NicknameInteractor()
        if(currentUser==null){
            Toast.makeText(requireContext(),"유저 아이디를 찾을 수 없습니다. 다시 로그인해주세요.",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }else{
            nickNameInteractor.getDocumentData(nickNameInteractor.ref.document(currentUser.uid))
        }

        nickNameInteractor.publishData.subscribe {
            if(it.isNotEmpty())  binding.feedNickname.setText("\' ${it.first().username} \' 님 환영해요!")
        }
        val deco= RecyclerViewDecoration(0,0,0,15,requireContext())
        val categoryList=resources.getStringArray(R.array.categories).toMutableList()
        categoryList.removeAt(0)
        val adapter=FeedAdapter(categoryList)
        val feedInteractor=FeedInteractor()
        feedInteractor.getData(feedInteractor.ref)
        feedInteractor.publishData.subscribe {
            adapter.refreshData(it)
            binding.feedSwipeRefreshLayout.isRefreshing=false
        }
        feedInteractor.publishIsSuccess.subscribe{
            binding.feedSwipeRefreshLayout.isRefreshing=false
        }


        adapter.setOnCategorySelectChanged(object:FeedAdapter.CategoryCallBackListener{
            override fun onCategorySelectChangeCallback(clickedItem: List<String>) {
                //TODO(카테고리 선택 내용 올라옴)
                if(clickedItem.isNotEmpty()){
                    val query=feedInteractor.ref.whereIn("category",clickedItem)
                    feedInteractor.getData(query)
                }
                else{
                    feedInteractor.getData(feedInteractor.ref)
                }
            }
        })

      //  adapter.refreshData(sampleData)
        binding.feedContentsRecyclerVeiw.apply{
            addItemDecoration(deco)
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false))
        }

        binding.feedFloatingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_writeFragment)
        }

        binding.feedSwipeRefreshLayout.setOnRefreshListener {
            feedInteractor.getData(feedInteractor.ref)

        }

        binding.feedLogo.setOnClickListener {
            binding.feedContentsRecyclerVeiw.smoothScrollToPosition(0)
        }



    }
    //backpress구현
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                ActivityCompat.finishAffinity(requireActivity())
                System.runFinalization();
                System.exit(0);
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}