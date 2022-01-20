package com.sshevtsov.translator.data.api

import android.util.Log
import com.sshevtsov.translator.data.api.ApiConstants.BASE_ENDPOINT
import com.sshevtsov.translator.data.api.ApiConstants.SEARCH
import com.sshevtsov.translator.data.api.model.DataModelResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslatorApi {

    @GET(SEARCH)
    suspend fun search(@Query("search") wordToSearch: String): List<DataModelResponse>

    companion object {

        fun create(): TranslatorApi =
            Retrofit.Builder()
                .baseUrl(BASE_ENDPOINT)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TranslatorApi::class.java)

        private fun createOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

        private val httpLoggingInterceptor: HttpLoggingInterceptor
            get() = HttpLoggingInterceptor { message ->
                Log.i("Network", message)
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }

}