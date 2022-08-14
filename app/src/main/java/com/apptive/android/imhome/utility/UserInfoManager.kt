package com.apptive.android.imhome.utility

object UserInfoManager {
    private var nickname:String?=null

    fun setNickname(name:String) {
        nickname=name
    }

    fun getNickname():String?= nickname

}