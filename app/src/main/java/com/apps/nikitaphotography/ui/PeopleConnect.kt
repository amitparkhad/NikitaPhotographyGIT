package com.apps.nikitaphotography.ui

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.apps.nikitaphotography.R
import com.apps.nikitaphotography.data.DialogModel
import com.apps.nikitaphotography.data.NikiPhotoDB
import com.apps.nikitaphotography.data.PeopleConnectDO
import com.apps.nikitaphotography.utils.DbWorkerThread
import com.apps.nikitaphotography.utils.Utils
import kotlinx.android.synthetic.main.activity_people_connect.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class PeopleConnect : AppCompatActivity(), View.OnClickListener, DialogInterface.OnClickListener {


    private var mDatabase = NikiPhotoDB.getInstance(this)
    private lateinit var mDbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_connect)


        btn_save.setOnClickListener(this)
        btn_Clear.setOnClickListener(this)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_save -> onSaveClicked()
            R.id.btn_Clear -> onClearClicked()
        }
    }

    private fun onSaveClicked() {
        val peopleConnectDO = PeopleConnectDO(
            et_Name.text.toString(),
            et_Email.text.toString(),
            et_phone.text.toString(),
            et_facebook.text.toString(),
            et_notes.text.toString(),
            chkbx_mailOptin.isChecked,
            false
        )
        if (validate(peopleConnectDO))
            insertDataInDb(peopleConnectDO)
    }

    private fun validate(peopleConnectDO: PeopleConnectDO): Boolean {
        var focusView: View? = null
        var bVal: Boolean = true

        if (peopleConnectDO.Name.isEmpty()) {
            et_Name.error = getString(R.string.error_field_required)
            focusView = et_Name
            bVal = false
        } else if (peopleConnectDO.email.isEmpty()) {
            et_Name.error = getString(R.string.error_field_required)
            focusView = et_Email
            bVal = false
        }

        if (bVal)
            focusView?.requestFocus()

        return bVal
    }

    private fun onClearClicked() {
        et_Name.setText("")
        et_Email.setText("")
        et_facebook.setText("")
        et_phone.setText("")
        et_notes.setText("")
    }

    override fun onDestroy() {
        NikiPhotoDB.destroyInstance()
        super.onDestroy()
    }

    private fun insertDataInDb(people: PeopleConnectDO) {
        val task = Runnable {
            var rowInserted: Long? = mDatabase?.peopleConnect()?.insert(people)
            if (rowInserted != null && rowInserted > 0) {
                var extended = ""
                if (people.optInEmail)
                    extended =
                            " As you Opted-In for the my contact details," +
                            "I will reachout to you on your mailId \"${people.email}\" soon..!"
                val model = DialogModel(
                    this, this, "Details Noted...!",
                    "Thanks ${people.Name}, \n\nI have saved your details..$extended",
                    "Okey Dokey :D"
                )
                Utils.showDialog(model)
            }
        }
        mDbWorkerThread.postTask(task)
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        dialog!!.dismiss()
        onClearClicked()
    }

}
