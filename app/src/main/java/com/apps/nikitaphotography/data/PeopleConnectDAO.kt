package com.apps.nikitaphotography.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface PeopleConnectDAO{

    @Query("SELECT * from PeopleConnect")
    fun getAllPeopleConnects():List<PeopleConnectDO>

    @Insert(onConflict = REPLACE)
    fun insert(peopleConnect: PeopleConnectDO):Long

    @Query("DELETE from PeopleConnect")
    fun deleteAll()

}