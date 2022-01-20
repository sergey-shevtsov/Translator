package com.sshevtsov.translator.di

import com.sshevtsov.translator.data.api.TranslatorApi
import com.sshevtsov.translator.data.mappers.DataModelMapper
import com.sshevtsov.translator.data.mappers.MeaningsMapper
import com.sshevtsov.translator.data.mappers.TranslationMapper
import com.sshevtsov.translator.data.repositories.RepositoryImplementation
import com.sshevtsov.translator.domain.model.DataModel
import com.sshevtsov.translator.domain.repositories.Repository
import com.sshevtsov.translator.presentation.search.SearchViewModel
import com.sshevtsov.translator.util.DefaultDispatcherProvider
import com.sshevtsov.translator.util.DispatcherProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {

    factory { TranslationMapper() }
    factory { MeaningsMapper(get()) }
    factory { DataModelMapper(get()) }
    single { TranslatorApi.create() }
    single<Repository<DataModel>> { RepositoryImplementation(get(), get()) }
    factory<DispatcherProvider> { DefaultDispatcherProvider() }
}

val searchScreen = module {

    viewModel { SearchViewModel(get(), get()) }
}