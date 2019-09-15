package com.manoelsrs.marvelchallenge.repository.remote

import com.manoelsrs.marvelchallenge.BuildConfig
import com.manoelsrs.marvelchallenge.repository.remote.characters.RemoteCharactersRepository
import com.manoelsrs.marvelchallenge.repository.remote.characters.producers.MD5Hash
import com.manoelsrs.marvelchallenge.repository.remote.characters.resources.RemoteCharactersResources
import com.manoelsrs.marvelchallenge.repository.remote.characters.services.CharactersServices

class RemoteRepository : RemoteFactory {
    override val characters: RemoteCharactersResources = RemoteCharactersRepository(
        Api<CharactersServices>().create(
            CharactersServices::class.java,
            BuildConfig.MARVEL_BASE_URL
        ),
        MD5Hash()
    )
}