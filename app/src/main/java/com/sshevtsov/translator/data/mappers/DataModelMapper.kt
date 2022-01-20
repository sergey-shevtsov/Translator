package com.sshevtsov.translator.data.mappers

import com.sshevtsov.translator.data.api.model.DataModelResponse
import com.sshevtsov.translator.domain.model.DataModel
import javax.inject.Inject

class DataModelMapper @Inject constructor(private val meaningsMapper: MeaningsMapper) {

    private fun toDomain(dataModelResponse: DataModelResponse): DataModel {

        requireNotNull(dataModelResponse.meanings)

        return DataModel.of(
            dataModelResponse.text,
            meaningsMapper.toDomain(dataModelResponse.meanings)
        )
    }

    fun toDomain(dataModelResponses: List<DataModelResponse>): List<DataModel> {
        return dataModelResponses.map { toDomain(it) }
    }
}