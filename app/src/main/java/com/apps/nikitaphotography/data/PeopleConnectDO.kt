package com.apps.nikitaphotography.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "PeopleConnect")
data class PeopleConnectDO(@ColumnInfo(name = "Name")val Name:String,
                           @ColumnInfo(name = "emailId")val email:String,
                           @ColumnInfo(name = "PhoneNo")val phoneNo:String,
                           @ColumnInfo(name = "FacebookId")val faceBookId:String,
                           @ColumnInfo(name = "notes")val notes:String,
                           @ColumnInfo(name = "optInEmail")val optInEmail:Boolean,
                           @ColumnInfo(name = "isEmailSent")val isMailSent:Boolean,
                           @PrimaryKey(autoGenerate = true)var id:Long = 0)
