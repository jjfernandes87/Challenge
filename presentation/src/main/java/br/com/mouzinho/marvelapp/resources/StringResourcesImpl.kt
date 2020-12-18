package br.com.mouzinho.marvelapp.resources

import android.content.Context
import br.com.mouzinho.domain.resources.StringResources
import br.com.mouzinho.marvelapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringResourcesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StringResources {

    override val updateFavoriteError: String get() = context.getString(R.string.update_favorite_error)
    override val genericErrorMessage: String get() = context.getString(R.string.generic_error)
}