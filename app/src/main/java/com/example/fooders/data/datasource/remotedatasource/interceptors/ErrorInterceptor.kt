package com.example.fooders.data.datasource.remotedatasource.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val response = chain.proceed(request)
        Log.d("myLog", "Request: $request \n Response: $response")
        if (response.code >= 400)
            Log.d("requestError", "Request: $request \n Response: $response")
        return response
    }
}