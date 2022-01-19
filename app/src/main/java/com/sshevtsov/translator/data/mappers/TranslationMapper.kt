package com.sshevtsov.translator.data.mappers

import com.sshevtsov.translator.data.api.model.TranslationResponse
import com.sshevtsov.translator.domain.model.Translation

class TranslationMapper {

    fun toDomain(translationResponse: TranslationResponse): Translation =
        Translation.of(translationResponse.text)

}