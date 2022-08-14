package com.apptive.android.imhome.feed

import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseAdapter
import kotlinx.coroutines.selects.select

class CategorySelectAdapter:BaseAdapter<CategorySelectAdapter.CategorySelectViewHolder,String>() {

    private val selectedCategory=mutableSetOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySelectViewHolder {
        val checkBox=CheckBox(parent.context)
        return CategorySelectViewHolder(checkBox)
    }

    override fun onBindViewHolder(holder: CategorySelectViewHolder, position: Int) {
        holder.categorySet(itemList[position])
    }

    inner class CategorySelectViewHolder(val checkBox: CheckBox): RecyclerView.ViewHolder(checkBox){
        fun categorySet(category:String){
            checkBox.apply{
                setText(category)

                setBackgroundResource(R.drawable.checkbox_custom)
                setButtonDrawable(null)
                setPadding(30,10,30,10)
                //setMinWidth(50)
                setLayoutParams(ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT))
                selectedCategory.forEach {
                    if(category==it){
                        setChecked(true)
                        changeTextColor(true)
                    }

                }
                setOnCheckedChangeListener { compoundButton, b ->
                    if(b){
                        selectedCategory.add(category)
                    }
                    else{
                        selectedCategory.remove(category)
                    }
                    changeTextColor(b)
                    listener?.onCallback(selectedCategory.toList())
                }

            }

        }
    }

    private fun CheckBox.changeTextColor(isChecked:Boolean){
        if(isChecked)setTextColor(context.getColor(R.color.white))
        else setTextColor(context.getColor(R.color.black))
    }
    fun setSelectedCategory(list:List<String>){
        if(selectedCategory.isEmpty()) this.selectedCategory.addAll(list)
    }
}