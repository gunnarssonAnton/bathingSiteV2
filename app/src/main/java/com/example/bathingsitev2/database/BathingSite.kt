package com.example.bathingsitev2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bathing_site_table")
data class BathingSite(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    val name:String?,
    val description:String?=null,
    val address:String?=null,
    val longitude:String?,
    val latitude:String?,
    val grade:String?=null,
    val water_temp:String?=null,
    val date_for_temp:String?=null
)
