package com.sshevtsov.translator.di

import com.sshevtsov.translator.presentation.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MappersModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        RxModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    interface Builder {

        fun build(): AppComponent

    }

    fun inject(searchFragment: SearchFragment)

}