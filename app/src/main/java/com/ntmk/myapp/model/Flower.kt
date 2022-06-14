package com.ntmk.myapp.model

import androidx.lifecycle.ViewModel
import java.io.Serializable

class Flower : Serializable {
    var id: String = ""
    var name: String? = null
    var price: String? = null
    var tag: String? = null
    var info: String? = null
    var humidity: Int? = null
    var weight: Int? = null
    var temperature: Int? = null
    var light: Int? = null
    var img: String? = null

}