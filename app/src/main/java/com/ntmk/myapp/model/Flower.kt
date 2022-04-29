package com.ntmk.myapp.model

class Flower {
    var id: Int? = 0
    var name: String? = null
    var price: String? = null
    var tag: String? = null
    var info: String? = null
    var humidity: Int? = null
    var weight: Int? = null
    var temperature: Int? = null
    var light: Int? = null
    var img: String? = null

    constructor() {}
    constructor(id: Int?, name: String?, price: String?, tag: String?, info: String?,
        humidity: Int?, weight: Int?, temperature: Int?, light: Int?, img: String? ) {
        this.id = id
        this.name = name
        this.price = price
        this.tag = tag
        this.info = info
        this.humidity = humidity
        this.weight = weight
        this.temperature = temperature
        this.light = light
        this.img = img
    }

    constructor(id: Int?, name: String?,price:String?, img: String?) {
        this.id = id
        this.name = name
        this.price = price
        this.img = img
    }

}