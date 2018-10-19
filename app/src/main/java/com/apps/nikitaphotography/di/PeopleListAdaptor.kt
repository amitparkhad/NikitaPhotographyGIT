package com.apps.nikitaphotography.di

import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apps.nikitaphotography.R
import com.apps.nikitaphotography.data.PeopleConnectDO


class PeopleListAdaptor (context: Context, private val mData: List<PeopleConnectDO>) :
    RecyclerView.Adapter<PeopleListAdaptor.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.people_list_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val peopleConnect = mData[position]
        holder.tvName.text = peopleConnect.Name
        holder.tvEmail.text = peopleConnect.email
        holder.tvFaceBook.text = peopleConnect.faceBookId
        holder.tvPhone.text = peopleConnect.phoneNo
        if (peopleConnect.optInEmail && !peopleConnect.isMailSent)
            holder.btnSendEmail.visibility = View.VISIBLE
        else
            holder.btnSendEmail.visibility = View.GONE

    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var tvName: TextView = itemView.findViewById(R.id.tvName)
        var tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        var tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        var tvFaceBook: TextView = itemView.findViewById(R.id.tvFaceBook)
        var btnSendEmail: AppCompatButton = itemView.findViewById(R.id.btn_sendMail)

        init {
            btnSendEmail.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, getItem(adapterPosition))
        }
    }

    // convenience method for getting data at click position
     fun getItem(id: Int): PeopleConnectDO {
        return mData[id]
    }

    // allows clicks events to be caught
     fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View, peopleConnect: PeopleConnectDO)
    }
}