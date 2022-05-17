package com.ntmk.myapp.model

import androidx.lifecycle.ViewModel
import java.io.Serializable

class FlowerCart : Serializable {
    var id: Int = 0
    var idFlower: Int = 0
    var name: String = ""
    var price: String = ""
    var img: String? = null
    var quantity : Int = 0
    
}