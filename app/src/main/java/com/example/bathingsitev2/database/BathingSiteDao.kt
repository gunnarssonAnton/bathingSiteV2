package com.example.bathingsitev2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BathingSiteDao {
    /**
     * used to get all bathing site objects from the database
     */
    @Query("SELECT * FROM bathing_site_table")
    fun getAll():List<BathingSite>

    /**
     * used to get all objects with the a specific id
     */
    @Query("SELECT * FROM bathing_site_table WHERE id LIKE :id")
    fun getSpecificSite(id:Int):BathingSite

    /**
     * used to object the database
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bathingSite: BathingSite)
}