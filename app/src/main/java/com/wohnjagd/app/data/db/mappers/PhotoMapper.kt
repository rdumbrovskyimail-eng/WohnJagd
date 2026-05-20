package com.wohnjagd.app.data.db.mappers

import com.wohnjagd.app.data.db.entities.PhotoEntity
import com.wohnjagd.app.domain.model.Photo

object PhotoMapper {

    fun toDomain(entity: PhotoEntity): Photo = Photo(
        url = entity.url,
        pHash = entity.pHash,
        localPath = entity.localPath,
        order = entity.orderIndex
    )
}