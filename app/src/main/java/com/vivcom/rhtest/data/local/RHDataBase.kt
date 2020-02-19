package com.vivcom.rhtest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vivcom.domain.Employed

@Database(entities = [Employed::class], version = 1)
abstract class RHDataBase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RHDataBase::class.java,
            "rh-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun employedDao(): EmployedDao
}