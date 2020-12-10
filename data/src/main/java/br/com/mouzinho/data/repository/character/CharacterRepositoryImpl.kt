package br.com.mouzinho.data.repository.character

import br.com.mouzinho.data.entity.character.ApiCharacter
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.character.Character
import br.com.mouzinho.domain.entity.character.CharacterResult
import br.com.mouzinho.domain.mapper.Mapper
import br.com.mouzinho.domain.repository.character.CharacterRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.cast
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterMapper: Mapper<ApiCharacter, Character>
) : CharacterRepository {

    override fun getCharacters(limit: Int, offset: Int): Observable<CharacterResult> =
        apiService.getCharacters(limit, offset)
            .map { response ->
                val characters =
                    response.data?.results?.map(characterMapper::transform) ?: emptyList()
                CharacterResult.Success(characters)
            }
            .cast<CharacterResult>()
            .onErrorReturn { error -> CharacterResult.Failure(error) }
            .startWith(CharacterResult.Loading)
}