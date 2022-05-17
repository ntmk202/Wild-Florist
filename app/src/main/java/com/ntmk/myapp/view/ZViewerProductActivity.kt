package com.ntmk.myapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ntmk.myapp.controller.CartFirebase
import com.ntmk.myapp.databinding.ZViewerProductBinding
import com.ntmk.myapp.model.Flower
import com.ntmk.myapp.model.FlowerCart
import com.squareup.picasso.Picasso


class ZViewerProductActivity : AppCompatActivity() {
    private lateinit var binding: ZViewerProductBinding
    var database : CartFirebase = CartFirebase()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ZViewerProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database.getDataFlowerCart()

        var mBundle : Bundle = intent.extras!!
        if (mBundle == null ){
            return
        }
        var flower : Flower = mBundle.get("Flower") as Flower
        binding.viewHomeName.setText(flower.name)
        binding.viewHomePrice.setText(flower.price)

        Picasso.get()
            .load(flower.img)
            .into(binding.imgVProduct);

        binding.tvInterview.setText(flower.info)
        binding.txtHumidity.setText(""+flower.humidity+"%")
        binding.txtTemperature.setText(""+flower.temperature+"%")
        binding.txtLight.setText(""+flower.light+"°C")
        binding.txtWeight.setText(""+flower.weight+"g")

        binding.linkBack.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }
        binding.btnAddProductToCart.setOnClickListener {
            var listFlowerCart: ArrayList<FlowerCart> = ArrayList()
            listFlowerCart = database.getListFlowerCart()
            var mFlowerCart : FlowerCart = FlowerCart()

            mFlowerCart.idFlower = flower.id!!
            mFlowerCart.name = flower.name!!
            mFlowerCart.price = flower.price!!
            mFlowerCart.img = flower.img!!

            var check : Boolean = false
            for (flower in listFlowerCart) {
                if(mFlowerCart.idFlower == flower.idFlower){
                    mFlowerCart.id = flower.id
                    mFlowerCart.quantity = flower.quantity + binding.txtQuantity.text.toString().toInt()
                    check = true
                }
            }
            if(!check){
                if (listFlowerCart.size == 0){
                    mFlowerCart.id = 0
                }else{
                    mFlowerCart.id = listFlowerCart.get(listFlowerCart.size - 1).id + 1
                }
                mFlowerCart.quantity = binding.txtQuantity.text.toString().toInt()
            }

            database.addFlowerCart(mFlowerCart)
            Toast.makeText(this,"Thêm sản phẩm thành công", Toast.LENGTH_LONG).show()
        }
        binding.btnDecreaseqQuantity.setOnClickListener {
            if(binding.txtQuantity.text.toString().toInt()>1) {
                binding.txtQuantity.setText((binding.txtQuantity.text.toString().toInt() - 1).toString())
            }else{
                binding.txtQuantity.setText("1")
            }
        }
        binding.btnIncreaseqQuantity.setOnClickListener {
            binding.txtQuantity.setText((binding.txtQuantity.text.toString().toInt() + 1).toString())
        }

    }
}