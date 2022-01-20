package com.sshevtsov.translator.di

import com.sshevtsov.translator.data.mappers.DataModelMapper
import com.sshevtsov.translator.data.mappers.MeaningsMapper
import com.sshevtsov.translator.data.mappers.TranslationMapper
import dagger.Module
import dagger.Provides

@Module
class MappersModule {

    @Provides
    fun provideTranslationMapper(): TranslationMapper = TranslationMapper()

    @Provides
    fun provideMeaningsMapper(translationMapper: TranslationMapper): MeaningsMapper =
        MeaningsMapper(translationMapper)

    @Provides
    fun provideDataModelMapper(meaningsMapper: MeaningsMapper): DataModelMapper =
        DataModelMapper(meaningsMapper)

}