package com.example.bathingsitev2.repository

import com.example.bathingsitev2.database.BathingSite
import com.example.bathingsitev2.database.BathingSiteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class Repository @Inject constructor(private val bathingSiteDao: BathingSiteDao) {

    fun getAllSites(): List<BathingSite>{
        return bathingSiteDao.getAll()
    }
    fun getSpecificSite(id: Int): BathingSite {
        return bathingSiteDao.getSpecificSite(id = id)
    }

    suspend fun insertBathingSite(bathingSite: BathingSite){
        withContext(Dispatchers.IO){
            bathingSiteDao.insert(bathingSite)
        }
    }

}