package com.apptive.android.imhome.feed

import android.util.Log
import com.apptive.android.imhome.baseClass.BaseInteractor
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import java.time.DayOfWeek
import java.util.*

class FeedInteractor : BaseInteractor<Feed>() {
    override val collectionPath: String
    get() = "feed"

    override fun parseData(document: DocumentSnapshot): Feed {

        val id=document.getId()
        val name=document["name"] as? String
        val date=document["date"] as? Timestamp
        val image=document["image"] as? String
        val contents=document["contents"] as? String
        val category=document["category"] as? String

        Log.d("checkforfor",category.toString())

        val feed=Feed(id,name.toString(), date?.toDate(),null,contents.toString(),category.toString())

        return feed
    }


}