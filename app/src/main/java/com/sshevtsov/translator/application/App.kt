package com.sshevtsov.translator.application

import android.app.Application
import com.sshevtsov.translator.di.AppComponent
import com.sshevtsov.translator.di.DaggerAppComponent

class App : Application() {

    companion object {
        val component: AppComponent by lazy {
            DaggerAppComponent.builder().build()
        }
    }

}