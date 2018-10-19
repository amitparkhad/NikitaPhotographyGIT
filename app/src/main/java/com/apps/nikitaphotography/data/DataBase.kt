package com.apps.nikitaphotography.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(PeopleConnectDO::class), version = 1)
abstract class NikiPhotoDB : RoomDatabase() {

    abstract fun peopleConnect(): PeopleConnectDAO

    companion object {
        private var INSTANCE: NikiPhotoDB? = null

        fun getInstance(context: Context): NikiPhotoDB? {
            if (INSTANCE == null) {
                synchronized(NikiPhotoDB::class) {
                    INSTANCE = Room.databaseBuilder(context,
                        NikiPhotoDB::class.java, "NikiPhotography.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}