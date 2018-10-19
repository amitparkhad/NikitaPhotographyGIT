package com.apps.nikitaphotography.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.apps.nikitaphotography.R
import com.apps.nikitaphotography.data.NikiPhotoDB
import com.apps.nikitaphotography.data.PeopleConnectDO
import com.apps.nikitaphotography.di.PeopleListAdaptor
import com.apps.nikitaphotography.utils.DbWorkerThread
import com.apps.nikitaphotography.utils.Utils
import kotlinx.android.synthetic.main.activity_people_list.*


class PeopleList : AppCompatActivity(), PeopleListAdaptor.ItemClickListener {

    private var mPeopleList = ArrayList<PeopleConnectDO>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mDatabase = NikiPhotoDB.getInstance(this)
    private lateinit var mDbWorkerThread: DbWorkerThread
    private lateinit var mPeopleListAdapter: PeopleListAdaptor


    override fun onResume() {
        super.onResume()
        getListItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_list)
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        // set up the RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        rvPeopleList.layoutManager = linearLayoutManager

        mPeopleListAdapter = PeopleListAdaptor(this, mPeopleList)
        mPeopleListAdapter.setClickListener(this)
        rvPeopleList.adapter = mPeopleListAdapter
    }

    override fun onItemClick(view: View, peopleConnectDO: PeopleConnectDO) {
        Utils.sendContactMail(peopleConnectDO,this)
    }

    private fun getListItem() {
        val task = Runnable {
            val allPeopleConnects = mDatabase?.peopleConnect()?.getAllPeopleConnects()
            if (allPeopleConnects!!.isNotEmpty()) {
                runOnUiThread {
                    tvNoData.visibility = View.GONE
                    mPeopleList.clear()
                    mPeopleList.addAll(allPeopleConnects)
                    mPeopleListAdapter.notifyItemInserted(allPeopleConnects.size)
                }
            } else {
                tvNoData.visibility = View.VISIBLE
            }
        }
        mDbWorkerThread.postTask(task)
    }
}
