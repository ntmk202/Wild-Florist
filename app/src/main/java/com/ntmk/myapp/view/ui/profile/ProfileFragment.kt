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
import com.ntmk.myapp.model.User
import com.ntmk.myapp.view.LoginActivity
import com.ntmk.myapp.view.RegistrationActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirebaseuser: FirebaseUser
    private var list_user: ArrayList<User> = ArrayList()
    var userFirebase: UserFirebase = UserFirebase()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mAuth = FirebaseAuth.getInstance()
        mFirebaseuser = mAuth.currentUser!!
        userFirebase.getData()


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        changeTextView()
        val settingFragment = SettingFragment()
        binding.linkSetting.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.nav_profile, settingFragment, SettingFragment::class.java.simpleName)
                    .addToBackStack(null).commit()
            }
        }

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

    fun changeTextView() {
        //Show info
        mFirebaseuser = mAuth.currentUser!!
        var nameAuth: String = mFirebaseuser?.displayName.toString()
        var emailAuth: String = mFirebaseuser?.email.toString()
        binding.userName.setText(nameAuth)
        binding.txtName.setText(nameAuth)
        binding.userEmail.setText(emailAuth)
        binding.txtEmail.setText(emailAuth)

        var database = FirebaseDatabase.getInstance().getReference("Users")
        var list_user = ArrayList<User>()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children){
                    var user = data.getValue(User::class.java)
                    list_user.add(user as User)
                }
                for(user in list_user){
                    if(user.email.equals(emailAuth)){
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

    fun onClick() {
        //Change data
        var name = binding.txtName.text.toString()
        var email = binding.txtEmail.text.toString()
        var phone = binding.txtPhone.text.toString()
        var address = binding.txtAddress.text.toString()


        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        mFirebaseuser.updateProfile(profileUpdates)
        mFirebaseuser!!.updateEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "email changed Succes", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Email changed Failed", Toast.LENGTH_SHORT).show()
            }
        }


        list_user = userFirebase.getListUser()
        var list_name: ArrayList<String> = ArrayList()
        var list_email: ArrayList<String> = ArrayList()
        var email_beforeChange = binding.userEmail.text.toString()
        var userBeforeChange: User = User()
        for (user in list_user) {
            if (email_beforeChange.equals(user.email)) {
                userBeforeChange = user
                break
            }
        }
        var user = User(userBeforeChange.id, name, email, userBeforeChange.pass, phone, address)
        userFirebase.addUser(user)
        changeTextView()
    }
}
