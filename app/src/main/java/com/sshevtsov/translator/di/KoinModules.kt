package com.sshevtsov.translator.di

import androidx.room.Room
import com.sshevtsov.translator.data.api.TranslatorApi
import com.sshevtsov.translator.data.mappers.DataModelMapper
import com.sshevtsov.translator.data.mappers.HistoryMapper
import com.sshevtsov.translator.data.mappers.MeaningsMapper
import com.sshevtsov.translator.data.mappers.TranslationMapper
import com.sshevtsov.translator.data.repositories.RepositoryImplementation
import com.sshevtsov.translator.data.repositories.RepositoryLocalImpl
import com.sshevtsov.translator.data.room.HistoryDatabase
import com.sshevtsov.translator.domain.model.history.History
import com.sshevtsov.translator.domain.model.search.DataModel
import com.sshevtsov.translator.domain.repositories.Repository
import com.sshevtsov.translator.domain.repositories.RepositoryLocal
import com.sshevtsov.translator.presentation.search.SearchViewModel
import com.sshevtsov.translator.util.DefaultDispatcherProvider
import com.sshevtsov.translator.util.DispatcherProvider
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
val application = module {
    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, "HistoryDB").build() }
    single { get<HistoryDatabase>().historyDao() }
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

val historyScreen = module {
    single<RepositoryLocal<History>> { RepositoryLocalImpl(get(), get()) }
    single { HistoryMapper() }
}