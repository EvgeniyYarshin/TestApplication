package com.gora.studio.testapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gora.studio.testapplication.api.Resource
import com.gora.studio.testapplication.model.Json
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import org.json.JSONObject

@Dao
interface JsonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(json: Json)

    @Query("SELECT * FROM Json")
    fun getJsonAsync(): LiveData<Json>
}