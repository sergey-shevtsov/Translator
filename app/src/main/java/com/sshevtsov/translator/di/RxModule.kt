package com.sshevtsov.translator.di

import com.sshevtsov.translator.util.SchedulersProvider
import com.sshevtsov.translator.util.SchedulersProviderImplementation
import dagger.Module
import dagger.Provides

@Module
class RxModule {

    @Provides
    fun provideSchedulersProvider(): SchedulersProvider =
        SchedulersProviderImplementation()

}