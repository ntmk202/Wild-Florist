package com.ntmk.myapp.service

import com.ntmk.myapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API_Builder {
    val BASE_URL = "https://wild-florist-d20-default-rtdb.firebaseio.com/"
    var retrofit : Retrofit =  Retrofit.Builder()
        .baseUrl(API_Builder.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var api : API_Interface = retrofit.create(API_Interface::class.java)
    fun getList(): ArrayList<User>? {
        var listUser : ArrayList<User>? = null
        var call : Call<List<User>> = api.getUserList()
        call.enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response==null || response.body()==null){
                    return
                }
                listUser = response.body() as ArrayList<User>
                for (i in listUser!!){
                    println(i.toString())
                }

            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {

            }
        })
        return  listUser
    }
    fun addUser(){
        var user : User = User(2,"A","xzwin","4567")
        var call : Call<User> = api.addUser("uu",user)
        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                println("Successfull")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("Successfull")
            }

        })
    }
}
