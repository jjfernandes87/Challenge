package com.manoelsrs.marvelchallenge.repository.remote

import com.manoelsrs.marvelchallenge.repository.remote.characters.resources.RemoteCharactersResources

interface RemoteFactory {
    val characters: RemoteCharactersResources
}