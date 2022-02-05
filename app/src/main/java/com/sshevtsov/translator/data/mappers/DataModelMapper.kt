package com.sshevtsov.translator.data.mappers

import com.sshevtsov.translator.data.api.model.DataModelResponse
import com.sshevtsov.translator.domain.model.search.DataModel

class DataModelMapper(private val meaningsMapper: MeaningsMapper) {

    fun toDomain(dataModelResponse: DataModelResponse): DataModel {

        requireNotNull(dataModelResponse.meanings)

        return DataModel.of(
            dataModelResponse.text,
            meaningsMapper.toDomain(dataModelResponse.meanings)
        )
    }

}