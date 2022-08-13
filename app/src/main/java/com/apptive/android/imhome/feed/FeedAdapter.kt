package com.apptive.android.imhome.feed

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseAdapter
import com.apptive.android.imhome.baseClass.RecyclerViewDecoration
import com.apptive.android.imhome.databinding.ItemFeedBinding
import com.apptive.android.imhome.utility.DateUtility
import java.util.*

class FeedAdapter:BaseAdapter<RecyclerView.ViewHolder,Feed>() {


    interface CategoryCallBackListener{
        fun onCategorySelectChangeCallback(clickedItem:List<String>)
    }
    var categorySelectListener:CategoryCallBackListener?=null
    fun setOnCategorySelectChanged(listener:CategoryCallBackListener){
        this.categorySelectListener=listener
    }

    private var selectedCategory=mutableListOf<String>()
    val sampleList=listOf("카테고리A","카테고리B","카테고리C")

    private val categoryAdapter=CategorySelectAdapter().apply{
        this.setSelectedCategory(selectedCategory)
        refreshData(sampleList)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position==0)0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            0->{
                val categorySelectRecyclerView=RecyclerView(parent.context).apply {
                    setLayoutManager(LinearLayoutManager(parent.context,LinearLayoutManager.HORIZONTAL,false))
                    setAdapter(categoryAdapter)
                }
                return SelectCategoryViewHolder(categorySelectRecyclerView)
            }
            1->{
                val binding=ItemFeedBinding.inflate(LayoutInflater.from(parent.context))
                return FeedUnitViewHolder(binding)
            }
            else->{
                val categorySelectRecyclerView=RecyclerView(parent.context)
                return SelectCategoryViewHolder(categorySelectRecyclerView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SelectCategoryViewHolder->{
                holder.setCategory()
            }
            is FeedUnitViewHolder->{
                holder.bind(itemList[position-1])
            }
        }
    }

    override fun getItemCount(): Int = itemList.size+1


    inner class SelectCategoryViewHolder(private val view:RecyclerView):RecyclerView.ViewHolder(view){

        fun setCategory(){
            val deco=RecyclerViewDecoration(10,0,0,0,view.context)
            view.apply{

               // categoryAdapter.setSelectedCategory(selectedCategory)

                setAdapter(categoryAdapter)

                setLayoutParams(ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT))
                if(this.itemDecorationCount==0){
                    addItemDecoration(deco)
                }
            }
            categoryAdapter.onCallback(object :CallBackListener<String>{
                override fun onCallback(clickedItem: List<String>) {
                    selectedCategory=clickedItem.toMutableList()
                    categorySelectListener?.onCategorySelectChangeCallback(selectedCategory)
                }
            })


        }
    }
    inner class FeedUnitViewHolder(private val binding:ItemFeedBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Feed){
            //피드 바인딩
            binding.itemFeedNickname.setText(item.name)
            binding.itemFeedDate.setText(DateUtility.formatDate(item.date?:Date(),"yyyy.MM.dd"))
            binding.itemFeedContents.setText(item.contents)
            if(item.image==null){
                binding.itemFeedImage.setVisibility(View.GONE)
            }else{
                binding.itemFeedImage.setVisibility(View.VISIBLE)
            }

            binding.root.setLayoutParams(ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT))
        }

    }
}