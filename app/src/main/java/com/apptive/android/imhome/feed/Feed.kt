package com.apptive.android.imhome.feed

import java.sql.Date


data class Feed(val name:String, val date: java.util.Date?=java.util.Date(), val image:String?, val contents:String)
