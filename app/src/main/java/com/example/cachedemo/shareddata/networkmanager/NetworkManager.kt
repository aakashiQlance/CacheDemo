package com.example.cachedemo.shareddata.networkmanager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cachedemo.Config
import com.example.cachedemo.model.AnimeCharacter
import com.example.cachedemo.model.ApiError
import com.example.cachedemo.model.RequestState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

object NetworkManager {

    fun <T> requestData(
        call: Call<AnimeCharacter<T>>,
        callback: MutableLiveData<RequestState<T>>,
    ) {
        callback.postValue(RequestState(progress = true))
        call.enqueue(object : Callback<AnimeCharacter<T>> {
            override fun onResponse(
                call: Call<AnimeCharacter<T>>,
                response: Response<AnimeCharacter<T>>,
            ) {
                if (response.isSuccessful) {
                    if (!response.body()?.results.isNullOrEmpty()) {
                        callback.postValue(
                            RequestState(
                                progress = false,
                                apiResponse = response.body()
                            )
                        )
                    } else {
                        callback.postValue(
                            RequestState(
                                error = ApiError(
                                    Config.CUSTOM_ERROR,
                                    "No data available"
                                )
                            )
                        )
                    }
                }

            }

            override fun onFailure(call: Call<AnimeCharacter<T>>, t: Throwable) {
                if (call.isCanceled) {
                    Log.i("NetworkResponse", "request was cancelled")
                } else {
                    when (t) {
                        is HttpException -> {
                            val responseBody = t.response()?.errorBody()
                            Log.i("NetworkResponse", "$responseBody")
                        }
                        is SocketTimeoutException -> Log.i("NetworkResponse", "Timeout")
                        is ConnectException -> Log.i("NetworkResponse", "Connection Error")
                        is IOException -> Log.i("NetworkResponse", "Network Error")
                        else -> {
                            Log.i("NetworkResponse", "no data found")
                        }
                    }
                    callback.value = RequestState(
                        progress = false,
                        error = ApiError(Config.CUSTOM_ERROR, t.message)
                    )
                }
            }

        })
    }

    fun <T> requestData1(
        call: Call<T>,
        callback: MutableLiveData<RequestState<T>>,
    ) {
        callback.postValue(RequestState(progress = true))
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        callback.postValue(
                            RequestState(
                                progress = false,
                                apiCustomResponse = response.body()
                            )
                        )
                    } else {
                        callback.postValue(
                            RequestState(
                                error = ApiError(
                                    Config.CUSTOM_ERROR,
                                    "No data available"
                                )
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (call.isCanceled) {
                    Log.i("NetworkResponse", "request was cancelled")
                } else {
                    when (t) {
                        is HttpException -> {
                            val responseBody = t.response()?.errorBody()
                            Log.i("NetworkResponse", "$responseBody")
                        }
                        is SocketTimeoutException -> Log.i("NetworkResponse", "Timeout")
                        is ConnectException -> Log.i("NetworkResponse", "Connection Error")
                        is IOException -> Log.i("NetworkResponse", "Network Error")
                        else -> {
                            Log.i("NetworkResponse", "no data found")
                        }
                    }
                    callback.value = RequestState(
                        progress = false,
                        error = ApiError(Config.CUSTOM_ERROR, t.message)
                    )
                }
            }

        })
    }

}