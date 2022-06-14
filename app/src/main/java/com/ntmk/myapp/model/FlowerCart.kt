package com.ntmk.myapp.model

import java.io.Serializable

class FlowerCart : Serializable {
    var id: Int = 0
    var idFlower: String = ""
    var name: String = ""
    var price: String = ""
    var img: String? = null
    var quantity: Int = 0

}