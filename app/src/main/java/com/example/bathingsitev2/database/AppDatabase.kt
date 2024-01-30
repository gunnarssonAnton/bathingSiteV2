package com.example.bathingsitev2.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BathingSite::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bathingSiteDao():BathingSiteDao


/*    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        *//**
         * used to get the database
         *//*
        fun getDatabase(context: Context): AppDatabase{
            synchronized(this) {


                var instance = INSTANCE


                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE=instance
                }

                return instance
            }
        }

    }*/

}