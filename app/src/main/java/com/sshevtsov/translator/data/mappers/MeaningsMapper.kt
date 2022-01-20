package com.sshevtsov.translator.data.mappers

import com.sshevtsov.translator.data.api.model.MeaningsResponse
import com.sshevtsov.translator.domain.model.Meanings

class MeaningsMapper(private val translationMapper: TranslationMapper) {

    private fun toDomain(meaningsResponse: MeaningsResponse): Meanings {

        requireNotNull(meaningsResponse.translation)

        return Meanings.of(
            translationMapper.toDomain(meaningsResponse.translation),
            meaningsResponse.imageUrl,
            meaningsResponse.soundUrl
        )
    }

    fun toDomain(meaningsResponses: List<MeaningsResponse>): List<Meanings> =
        meaningsResponses.map { toDomain(it) }

}