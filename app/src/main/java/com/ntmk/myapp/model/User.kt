package com.ntmk.myapp.model

import java.io.Serializable

data class User(
//    var userId: Int = 0,
    var name: String? = null,
    var email: String? = null,
    var pass: String? = null,
    var phone: String? = null,
    var address:String? = null
)