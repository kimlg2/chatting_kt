package com.example.chatting.Adapter

import android.content.ClipData
import com.example.chatting.R
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.chat_right_me.view.*

class ChatRightMe(val msg: String) : ClipData.Item<GroupieViewHolder>() {


    override fun getLayout(): Int {
        return R.layout.chat_right_me
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.right_msg.text = msg
    }
}