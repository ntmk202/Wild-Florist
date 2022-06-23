package com.ntmk.myapp.model

class Order {
    var id: String = ""
    var timeOrder: String? = null
    var listFlower: ArrayList<FlowerCart>? = null
    var status: String? = null
    var orderTotal : String? = null
}