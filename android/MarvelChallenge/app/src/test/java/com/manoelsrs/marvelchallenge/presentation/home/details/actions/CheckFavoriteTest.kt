package com.manoelsrs.marvelchallenge.presentation.home.details.actions

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.Repository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.willReturn
import io.reactivex.Single
import org.junit.Test

class CheckFavoriteTest {

    private val favorite = Character(
        id = 1,
        name = "name",
        description = "description",
        photo = "photo",
        photoExtension = "photoExt",
        hasComics = true,
        hasSeries = false
    )

    private val favorites = listOf(
        favorite,
        favorite.copy(id = 2, name = "name2"),
        favorite.copy(id = 3, name = "name3"),
        favorite.copy(id = 4, name = "name4")
    )

    private val repository: Repository = mock {
        given { it.local }.willReturn { mock() }
        given { it.local.favorite }.willReturn { mock() }
        given { it.local.favorite.getFavoritesSingle() }.willReturn { Single.just(favorites) }
    }

    private val checkFavorite = CheckFavorite(repository)

    @Test
    fun `should return true when favorites exists`() {
        checkFavorite.execute(favorite.copy(id = 3, name = "name3"))
            .test()
            .assertValue(true)
    }

    @Test
    fun `should return false when favorites not exists`() {
        checkFavorite.execute(favorite.copy(id = 30, name = "name30"))
            .test()
            .assertValue(false)
    }
}