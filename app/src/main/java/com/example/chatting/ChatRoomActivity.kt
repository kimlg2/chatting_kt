package com.example.chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting.Adapter.ChatLeftYou
import com.example.chatting.Adapter.ChatRightMe
import com.example.chatting.Model.ChatModel
import com.example.chatting.Model.ChatNewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val TAG = ChatRoomActivity :: class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        auth = FirebaseAuth.getInstance()

        val myUid = auth.uid
        val yourUid = intent.getStringExtra("yourUid")
        val name = intent.getStringExtra("name")

        val adapter = GroupAdapter<GroupieViewHolder>()

//        adapter.add(ChatLeftYou())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatLeftYou())





//            val db = FirebaseFirestore.getInstance()
//        db.collection("message")
//            .orderBy("time")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.e(TAG, document.toString())
//                    val senderUid = document.get("myUid")
//                    val msg = document.get("message").toString()
//                    Log.e(TAG, senderUid.toString())
//                    Log.e(TAG, myUid!!)
//
//                    if (senderUid!!.equals(myUid)) {
//                        adapter.add(ChatRightMe(msg))
//                    } else {
//                        adapter.add(ChatLeftYou(msg))
//                    }
//                    //만약 내가 보낸 메세지일 떄
//                    //만약 내가 보낸 메세지가 아닐때
//                }
//                recyclerView_chat.adapter = adapter
//            }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        val readRef = database.getReference("message").child(myUid.toString()).child(yourUid.toString())
        val childEventListener = object : ChildEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }



            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    Log.d(TAG,"p0:" + p0)
                val model = p0.getValue(ChatNewModel::class.java)

                val msg = model?.message.toString()
                val who = model?.who
                if (who == "me") {
                    adapter.add(ChatRightMe(msg))
                } else {
                    adapter.add(ChatLeftYou(msg))
                }

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        }
        recyclerView_chat.adapter = adapter
        readRef.addChildEventListener(childEventListener)

        button.setOnClickListener {
            val message = editText.text.toString()

            val chat = ChatNewModel(myUid.toString(), yourUid.toString(),message, System.currentTimeMillis(),"me")
            myRef.child(myUid.toString()).child(yourUid.toString()).push().setValue(chat)

            val chat_get = ChatNewModel(yourUid.toString(), myUid.toString(),message, System.currentTimeMillis(),"you")
            myRef.child(yourUid.toString()).child(myUid.toString()).push().setValue(chat_get)
            editText.setText("")
//            val message = editText.text.toString()
//            editText.setText("")
//
//            val chat = ChatModel(myUid.toString(), yourUid.toString(),message, System.currentTimeMillis())
//            db.collection("message")
//                .add(chat)
//                .addOnSuccessListener {
//                        Log.d(TAG, "성공")
//                }
//                .addOnFailureListener {
//                    Log.d(TAG, "실패")
//                }
        }
    }
}