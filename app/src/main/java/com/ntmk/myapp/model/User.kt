package com.ntmk.myapp.model

import java.io.Serializable

data class User(
    var id: Int = 0,
    var name: String = "",
    var email: String = "",
    var pass: String = "",
    var phone: String? = "",
    var address:String? = ""
) : Serializable