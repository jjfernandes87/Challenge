package com.manoelsrs.marvelchallenge.repository.local

import com.manoelsrs.marvelchallenge.repository.local.character.resources.LocalCharacterResources

interface LocalFactory {
    val character: LocalCharacterResources
}