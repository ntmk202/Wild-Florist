package com.ntmk.myapp.controller

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.ntmk.myapp.model.User

class UserFirebase {
    private var list_user: ArrayList<User> = ArrayList()
    private var database: DatabaseReference

    init {
        database = FirebaseDatabase.getInstance().getReference("Users")
    }

    fun getListUser(): ArrayList<User>{
        return list_user
    }

    fun getData() {
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
    fun addUser(user : User){
        database.child(user.id.toString()).setValue(user).addOnSuccessListener {
            //Toast.makeText(this, "Registration success", Toast.LENGTH_SHORT).show()
        }.addOnCanceledListener {
            //Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
        }

    }

    }