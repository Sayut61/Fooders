package com.example.fooders.data.datasource.remotedatasource.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val modifiedRequest: Request = originalRequest.newBuilder()
            .header("x-api-key", "3a79a40c41c24959af2253c1c3ecf92a")
            .build()
        return chain.proceed(modifiedRequest)
    }
}