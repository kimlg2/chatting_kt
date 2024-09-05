package com.example.chatting.Adapter

import android.content.ClipData
import com.example.chatting.R
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.chat_left_you.view.*

class ChatLeftYou(val msg: String) : ClipData.Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_left_you
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.left_chat.text = msg
    }
}



