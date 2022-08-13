package com.apptive.android.imhome.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseFragment
import com.apptive.android.imhome.baseClass.RecyclerViewDecoration
import com.apptive.android.imhome.databinding.FragmentFeedBinding
import com.apptive.android.imhome.signup.NicknameInteractor
import java.util.*


class FeedFragment:BaseFragment() {

    private val args:FeedFragmentArgs by navArgs()

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
        nickNameInteractor.getDocumentData(nickNameInteractor.ref.document(args.userId))
        nickNameInteractor.publishData.subscribe {
            if(it.isNotEmpty())  binding.feedNickname.setText(it.first())
        }
        val deco= RecyclerViewDecoration(0,0,0,15,requireContext())
        val adapter=FeedAdapter()
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
                Toast.makeText(requireContext(),clickedItem.toString(),Toast.LENGTH_SHORT).show()
                //TODO(카테고리 선택 내용 올라옴)
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
}