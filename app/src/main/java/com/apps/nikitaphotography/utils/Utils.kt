package com.apps.nikitaphotography.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import android.content.DialogInterface
import android.content.Intent
import com.apps.nikitaphotography.R
import com.apps.nikitaphotography.data.DialogModel
import com.apps.nikitaphotography.data.PeopleConnectDO


class Utils{

    companion object {

          fun showDialog(obj: DialogModel){
             val builder = AlertDialog.Builder(obj.context)
             builder.setTitle(obj.title)
             builder.setMessage(obj.body)
             builder.setCancelable(false)
             builder.setNeutralButton(obj.neutralButton,obj.listener)
             builder.show()
         }

        fun sendContactMail(peopleConnectDO: PeopleConnectDO, context: Context){
             val emailIntent =  Intent( Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(peopleConnectDO.email))
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "PHOTOGRAPHER:: Nikita Bhisikar - Contact Details");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Dear ${peopleConnectDO.Name},\n\n"+ context.getString(R.string.emailBody)+ "\n\nThanks\nNikita")
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }

    }
}