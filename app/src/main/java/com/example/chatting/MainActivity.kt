package com.example.chatting

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.login_btn

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val TAG = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        signUp.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Log.d(TAG, "성공")
                        val uid = FirebaseAuth.getInstance().uid ?: ""

                        val user = User(uid, username.text.toString())
                        //데이터베이스에 넣음
                        val db = FirebaseFirestore.getInstance().collection("users")
                        db.document(uid)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "데이터베이스 성공")
                            }
                            .addOnFailureListener {
                                Log.d(TAG, "데이터베이스 실패")
                            }

                        val intent = Intent(this, ChatListActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    } else {
                        Log.d(TAG, "실패", task.exception)
                    }

                }
        }

            login_btn.setOnClickListener {


                val intent = Intent(this, loginActivity::class.java)
                startActivity(intent)
            }

        }
    }
