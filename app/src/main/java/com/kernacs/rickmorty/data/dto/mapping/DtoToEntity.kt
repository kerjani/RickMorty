package com.kernacs.rickmorty.data.dto.mapping

import com.kernacs.rickmorty.data.dto.CharactersResponseDto
import com.kernacs.rickmorty.data.dto.EpisodeDto
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.util.isoStringToDate


fun CharactersResponseDto.CharacterDto.toEntity() = CharacterEntity(
    id = id,
    created = created.isoStringToDate(),
    episodeIds = episode.map { it.split('/').last().toInt() },
    gender = gender,
    imageUrl = image,
    location = location.name,
    name = name,
    origin = origin.name,
    species = species,
    status = status,
    type = type,
    url = url,
)

fun EpisodeDto.toEntity() = EpisodeEntity(
    id = id,
    airDate = airDate,
    characters = characters.map { it.split('/').last().toInt() },
    created = created,
    episode = episode,
    name = name,
    url = url,
)