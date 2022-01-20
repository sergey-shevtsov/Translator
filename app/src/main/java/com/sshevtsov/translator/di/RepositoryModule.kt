package com.sshevtsov.translator.di

import com.sshevtsov.translator.data.api.TranslatorApi
import com.sshevtsov.translator.data.mappers.DataModelMapper
import com.sshevtsov.translator.data.repositories.RepositoryImplementation
import com.sshevtsov.translator.domain.model.DataModel
import com.sshevtsov.translator.domain.repositories.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        translatorApi: TranslatorApi,
        dataModelMapper: DataModelMapper
    ): Repository<DataModel> =
        RepositoryImplementation(translatorApi, dataModelMapper)

}