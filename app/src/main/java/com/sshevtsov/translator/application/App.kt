package com.sshevtsov.translator.application

import android.app.Application
import com.sshevtsov.translator.di.application
import com.sshevtsov.translator.di.historyScreen
import com.sshevtsov.translator.di.searchScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, searchScreen, historyScreen))
        }
    }

}