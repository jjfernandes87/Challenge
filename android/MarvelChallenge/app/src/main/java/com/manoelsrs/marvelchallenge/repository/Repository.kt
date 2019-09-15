package com.manoelsrs.marvelchallenge.repository

import com.manoelsrs.marvelchallenge.repository.local.LocalFactory
import com.manoelsrs.marvelchallenge.repository.local.LocalRepository
import com.manoelsrs.marvelchallenge.repository.remote.RemoteFactory
import com.manoelsrs.marvelchallenge.repository.remote.RemoteRepository

class Repository : RepositoryFactory {
    override val local: LocalFactory = LocalRepository()
    override val remote: RemoteFactory = RemoteRepository()
}