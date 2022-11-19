package com.manoelsrs.marvelchallenge.repository

import com.manoelsrs.marvelchallenge.repository.local.LocalFactory
import com.manoelsrs.marvelchallenge.repository.remote.RemoteFactory

interface RepositoryFactory {
    val local: LocalFactory
    val remote: RemoteFactory
}