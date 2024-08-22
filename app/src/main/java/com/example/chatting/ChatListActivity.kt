package com.example.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting.Adapter.Useritem
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_list.*

class ChatListActivity : AppCompatActivity() {
    private val TAG = ChatListActivity::class.java.simpleName
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
        myChatList.setOnClickListener {
            val intent = Intent(this, MyRoomActivity::class.java)
            startActivity(intent)
        }

        val adapter = GroupAdapter<GroupieViewHolder>()


        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    adapter.add(Useritem(document.get("username").toString(), document.get("uid").toString()))

                }
                recyclerview_list.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        adapter.setOnItemClickListener { item, view ->


            val uid = (item as Useritem).uid
            val name =  (item as Useritem).name

            val intent = Intent(this, ChatRoomActivity::class.java)
             intent.putExtra("yourUid", uid)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }

    private fun startActivity(intent: Any) {

    }
}