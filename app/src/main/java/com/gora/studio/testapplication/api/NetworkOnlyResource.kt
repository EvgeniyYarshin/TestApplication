package com.gora.studio.testapplication.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread

/**
 * A generic class to send loading event up-stream when fetching data
 * only from network.
 *
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkOnlyResource<RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<RequestType>>() //List<Repo>

    init {
        fetchFromNetwork()
    }

    @MainThread
    private fun setResultValue(newValue: Resource<RequestType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            setResultValue(Resource.success(processResponse(response)))
                        }
                    }
                }

                is ApiErrorResponse -> {
                    onFetchFailed()
                    setResultValue(Resource.error(response.errorMessage, null))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<RequestType>>

    @WorkerThread
    protected abstract fun saveCallResult(items: RequestType)

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}