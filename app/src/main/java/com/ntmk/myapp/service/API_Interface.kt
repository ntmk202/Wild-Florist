package com.ntmk.myapp.service
import com.ntmk.myapp.model.User
import retrofit2.Call
import retrofit2.http.*


interface API_Interface {
    @GET("/Users.json")
    fun getUserList() : Call<List<User>>
    @POST("/Users.json")
    fun addUser(@Header("Sessionkey") t : String, @Body  user:User) : Call<User>

}