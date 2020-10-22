package com.gora.studio.testapplication.api

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.GET

interface TestApi {

    @GET("pokemon/ditto")
    fun setJsonAsync(): LiveData<ApiResponse<ResponseBody>>
}