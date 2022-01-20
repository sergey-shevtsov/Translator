package com.sshevtsov.translator.di

import com.sshevtsov.translator.data.api.TranslatorApi
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideTranslatorApi(): TranslatorApi =
        TranslatorApi.create()

}