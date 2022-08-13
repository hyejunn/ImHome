package com.apptive.android.imhome.signup

import com.apptive.android.imhome.baseClass.BaseInteractor
import com.apptive.android.imhome.feed.Feed
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

class NicknameInteractor : BaseInteractor<String>() {
    override val collectionPath: String
        get() = "userName"

    override fun parseData(document: DocumentSnapshot): String {

        val id=document.getId()
        val name=document["username"] as? String
        return name.toString()
    }


}