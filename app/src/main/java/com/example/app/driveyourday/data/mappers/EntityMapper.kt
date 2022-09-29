package com.example.app.driveyourday.data.mappers

interface EntityMapper<Entity, Domain> {

    fun fromEntity(entity: Entity): Domain

    fun toEntity(domain: Domain): Entity

    fun fromEntityList(entities: List<Entity>): List<Domain> =
        entities.map { fromEntity(it) }

    fun toEntityList(domains: List<Domain>): List<Entity> =
        domains.map { toEntity(it) }
}