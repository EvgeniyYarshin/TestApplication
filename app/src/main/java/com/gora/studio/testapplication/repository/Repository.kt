package com.gora.studio.testapplication.repository

import androidx.lifecycle.LiveData
import com.gora.studio.testapplication.api.*
import com.gora.studio.testapplication.db.JsonDao
import com.gora.studio.testapplication.model.Json
import okhttp3.ResponseBody
import javax.inject.Inject

class Repository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val testApi: TestApi,
    private val jsonDao: JsonDao
) {
    fun getJson(): LiveData<Resource<Json>> {
        return object : DbOnlyResource<Json>(appExecutors) {
            override fun loadFromDb(): LiveData<Json> {
                return jsonDao.getJsonAsync()
            }
        }.asLiveData()
    }

    fun setJson(): LiveData<Resource<ResponseBody>> {
        return object : NetworkOnlyResource<ResponseBody>(appExecutors) {
            override fun saveCallResult(items: ResponseBody) {
                jsonDao.insert(
                    Json(
                        id = 0,
                        json = items.string()
                    )
                )
            }
            override fun createCall(): LiveData<ApiResponse<ResponseBody>> {
                return testApi.setJsonAsync()
            }
        }.asLiveData()
    }
}