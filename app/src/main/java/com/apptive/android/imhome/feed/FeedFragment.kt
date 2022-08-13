package com.apptive.android.imhome.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseFragment
import com.apptive.android.imhome.baseClass.RecyclerViewDecoration
import com.apptive.android.imhome.databinding.FragmentFeedBinding
import java.util.*


class FeedFragment:BaseFragment() {

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

        val sampleData=List(10,{Feed("홍길동", Date(),null,"sample contents")})
        val deco= RecyclerViewDecoration(0,0,0,15,requireContext())
        val adapter=FeedAdapter()
        adapter.setOnCategorySelectChanged(object:FeedAdapter.CategoryCallBackListener{
            override fun onCategorySelectChangeCallback(clickedItem: List<String>) {
                Toast.makeText(requireContext(),clickedItem.toString(),Toast.LENGTH_SHORT).show()
                //TODO(카테고리 선택 내용 올라옴)
            }
        })

        adapter.refreshData(sampleData)
        binding.feedContentsRecyclerVeiw.apply{
            addItemDecoration(deco)
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false))
        }

        binding.feedFloatingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_writeFragment)
        }

        binding.feedSwipeRefreshLayout.setOnRefreshListener {
            //TODO(데이터 새로고침)
            binding.feedSwipeRefreshLayout.isRefreshing=false
        }

        binding.feedLogo.setOnClickListener {
            binding.feedContentsRecyclerVeiw.smoothScrollToPosition(0)
        }

    }
}