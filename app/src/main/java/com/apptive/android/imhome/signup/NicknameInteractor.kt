package com.apptive.android.imhome.signup

import android.provider.ContactsContract
import com.apptive.android.imhome.baseClass.BaseInteractor
import com.apptive.android.imhome.feed.Feed
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

class NicknameInteractor : BaseInteractor<Username>() {
    override val collectionPath: String
        get() = "username"

    override fun parseData(document: DocumentSnapshot): Username {

        val id=document.getId()
        val name=document["username"] as? String
        return Username(name.toString())
    }


}