package com.gora.studio.testapplication.ui.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.gora.studio.testapplication.api.Resource
import com.gora.studio.testapplication.model.Json
import com.gora.studio.testapplication.repository.Repository
import com.gora.studio.testapplication.util.AbsentLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject

class RoomViewModel
@Inject constructor(private val repository: Repository): ViewModel() {
    private val getForJson = MutableLiveData<Boolean>()
    private val setForJson = MutableLiveData<Boolean>()

    fun retryGetJson() {
        getForJson.value = true
    }

    fun retrySetJson() {
        setForJson.value = true
    }

    val getJson: LiveData<Resource<Json>> = getForJson.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            repository.getJson()
        }
    }

    val setJson: LiveData<Resource<ResponseBody>> = setForJson.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            repository.setJson()
        }
    }
}