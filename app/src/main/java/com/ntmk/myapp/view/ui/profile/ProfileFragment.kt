package com.ntmk.myapp.view.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ntmk.myapp.R
import com.ntmk.myapp.controller.UserFirebase
import com.ntmk.myapp.databinding.FragmentProfileBinding
import com.ntmk.myapp.view.LoginActivity
import com.ntmk.myapp.model.User as User

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirebaseuser: FirebaseUser
    private var list_user: ArrayList<User> = ArrayList()
    private var userFirebase: UserFirebase = UserFirebase()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        mAuth = FirebaseAuth.getInstance()
        mFirebaseuser = mAuth.currentUser!!
        userFirebase.getData()


        changeTextView()
        // Chuyển giao diện về fragment Setting
        val settingFragment = SettingFragment()
        binding.linkSetting.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.nav_profile, settingFragment, SettingFragment::class.java.simpleName)
                    .addToBackStack(null).commit()
            }
        }

        // Chuyển giao diện về fragment Balance
        val balanceFragment = BalanceFragment()
        binding.linkBalance.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.nav_profile, balanceFragment, BalanceFragment::class.java.simpleName)
                    .addToBackStack(null).commit()
            }
        }

        binding.btnLogout.setOnClickListener {
            mAuth.signOut()
            val i = Intent(context, LoginActivity::class.java)
            startActivity(i)

        }
        binding.btnConfirm.setOnClickListener {
            onClick()
        }

        return binding.root
    }

    private fun changeTextView() {
        //Show info
        mFirebaseuser = mAuth.currentUser!!
        val nameAuth: String = mFirebaseuser.displayName.toString()
        val emailAuth: String = mFirebaseuser.email.toString()
        binding.userName.text = nameAuth
        binding.txtName.setText(nameAuth)
        binding.userEmail.text = emailAuth
        binding.txtEmail.setText(emailAuth)

        val database = FirebaseDatabase.getInstance().getReference("Users")
//            .child((mFirebaseuser?.uid!!))
        val list_user = ArrayList<User>()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children){
                    val user = data.getValue(User::class.java)
                    list_user.add(user as User)
                }
                for(user in list_user){
                    if(user.email == emailAuth){
                        binding.txtPhone.setText(user.phone)
                        binding.txtAddress.setText(user.address)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel",error.toString())
            }
        })

    }

    private fun onClick() {
        //Change data
        val name = binding.txtName.text.toString()
        val email = binding.txtEmail.text.toString()
        val phone = binding.txtPhone.text.toString()
        val address = binding.txtAddress.text.toString()

        // Update name authen
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        mFirebaseuser.updateProfile(profileUpdates)

        // Update email authen
        mFirebaseuser.updateEmail(email)

        // Update phone and address realtime
        list_user = userFirebase.getListUser()
        val email_beforeChange = binding.userEmail.text.toString()
        var userBeforeChange = User()

        for (user in list_user) {
            if (email_beforeChange == user.email) {
                userBeforeChange = user
                break
            }
        }

        val user = User(name, email, userBeforeChange.pass, phone, address)
        userFirebase.addUser(user)
        Toast.makeText(context,"Update success",Toast.LENGTH_SHORT).show()
        changeTextView()
    }
}
