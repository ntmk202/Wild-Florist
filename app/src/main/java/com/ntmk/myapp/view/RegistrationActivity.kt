package com.ntmk.myapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityRegistrationBinding
import com.ntmk.myapp.model.User

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var database : DatabaseReference
    private lateinit var list_user : ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration )
        init()


        binding.txtLinkLogin.setOnClickListener{
            val i= Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
        }


    }
    fun init(){
        list_user = ArrayList<User>()
        database = FirebaseDatabase.getInstance().getReference("Users")
    }
    private fun getData(){
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children){
                    var user = data.getValue(User::class.java)
                    list_user.add(user as User)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel",error.toString())
            }
        })
    }
//    private fun login() {
//        var user_name = txt_name?.text.toString()
//        var email = txt_email?.text.toString()
//        var pass = txt_pass?.text.toString()
//        var id_last:Int = 0
//        if(list_user.size>0){
//            id_last = list_user.get(list_user.size-1).getId()+1
//        }
////        var check : String = checkInfo(user_name,email,pass)
////        if(!check.equals("")){
////            Toast.makeText(this,check, Toast.LENGTH_SHORT).show()
////        }
//
//            val user = User(id_last,user_name,email,pass)
//            database.child(user.getId().toString()).setValue(user).addOnSuccessListener {
//                Toast.makeText(this,"Registration success", Toast.LENGTH_SHORT).show()
//            }.addOnCanceledListener {
//                Toast.makeText(this,"Registration failed", Toast.LENGTH_SHORT).show()
//            }
//
//    }
//    private fun checkInfo(name :String,email:String,pass:String):String{
//        var check : String = ""
//        var pattern : Regex = "\\w+".toRegex()
//        if(name.isEmpty() && email.isEmpty() && pass.isEmpty()){
//            check = "User,name or pass not to blank"
//        }
////        if(!checkRobot.isChecked()){
////            check = "You are a robot ?"
////        }
//        return check
//    }
}