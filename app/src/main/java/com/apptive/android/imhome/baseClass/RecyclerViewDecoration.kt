package com.apptive.android.imhome.baseClass

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDecoration(private val r: Int=0,private val l:Int=0,private val t:Int=0,private val b:Int=0,private val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply{
            right = r.dpToPx(context).toInt()
            left=l.dpToPx(context).toInt()
            top=t.dpToPx(context).toInt()
            bottom=b.dpToPx(context).toInt()
        }

    }

    private fun Int.dpToPx(context: Context):Float{

        val density = context.getResources().getDisplayMetrics().density
        return Math.round( this * density).toFloat()
    }

}