package com.apps.nikitaphotography.data

import android.content.Context
import android.content.DialogInterface

class DialogModel(
    val context: Context,
    val listener: DialogInterface.OnClickListener,
    val title: String,
    val body: String,
    val neutralButton: String
)