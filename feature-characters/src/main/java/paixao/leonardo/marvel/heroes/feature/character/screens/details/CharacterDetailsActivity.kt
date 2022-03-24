package paixao.leonardo.marvel.heroes.feature.character.screens.details

import android.content.Intent
import android.os.Bundle
import android.view.Gravity.CENTER
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import org.kodein.di.DI
import org.kodein.di.DIAware
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.feature.R
import paixao.leonardo.marvel.heroes.feature.character.screens.NavigationParams
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.utils.isVisible
import paixao.leonardo.marvel.heroes.feature.core.utils.ktx.collectIn
import paixao.leonardo.marvel.heroes.feature.core.utils.putParams
import paixao.leonardo.marvel.heroes.feature.core.utils.selfInject
import paixao.leonardo.marvel.heroes.feature.core.utils.viewModel
import paixao.leonardo.marvel.heroes.feature.databinding.ActivityCharacterDetailsBinding

class CharacterDetailsActivity : AppCompatActivity(), DIAware {

    override val di: DI = selfInject()

    private val viewModel by viewModel<CharacterDetailsViewModel>()

    private lateinit var binding: ActivityCharacterDetailsBinding

    val character
        get() = intent.getSerializableExtra(NavigationParams.CHARACTER_DETAILS_PARAMS) as? MarvelCharacter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun saveOrRemoveFavoriteCharacter(character: MarvelCharacter) {
        viewModel.saveOrRemoveFavoriteCharacter(character).collectIn(lifecycleScope) { event ->
            when (event) {
                is StateMachineEvent.Success -> defineStarImg(binding.icStar, event.value)
                is StateMachineEvent.Failure -> showErrorToast()
                else -> Unit
            }
        }
    }

    private fun showErrorToast() {
        Toast.makeText(this, R.string.error_removing_favorites, Toast.LENGTH_LONG).show()
    }

    private fun setupView() {
        val safeCharacter = character
        if (safeCharacter != null) {

            binding.toolbar.setNavigationOnClickListener {
                setupResult()

            }

            Glide.with(this)
                .load(safeCharacter.imageUrl)
                .into(binding.characterImageView)

            binding.characterDescription.text = safeCharacter.retrieveDescriptionText()

            defineStarImg(binding.icStar, safeCharacter.isFavorite)
            binding.icStar.setOnClickListener {
                saveOrRemoveFavoriteCharacter(safeCharacter)
            }
        } else {
            renderNotFoundError()
        }
    }

    private fun setupResult() {
        val lastCharacterData = viewModel.retrieveUpdatedCharacter() ?: character
        Intent().apply {
            putParams(
                bundleOf(
                    NavigationParams.CHARACTER_DETAILS_PARAMS to lastCharacterData
                )
            )
            setResult(RESULT_OK, this)
        }
        finish()
    }

    private fun defineStarImg(
        imageView: AppCompatImageView,
        isSaved: Boolean
    ) {

        val imsRes = if (isSaved)
            R.drawable.ic_filled_star
        else
            R.drawable.ic_star

        AppCompatResources.getDrawable(this, imsRes).let(imageView::setImageDrawable)
    }

    private fun MarvelCharacter.retrieveDescriptionText() =
        if (!description.isNullOrEmpty()) {
            description
        } else {
            binding.characterDescription.gravity = CENTER
            getString(R.string.character_details_empty_description)
        }


    private fun renderNotFoundError() {
        (binding.errorView.root).apply {
            isVisible = true
            renderError(
                marvelException = MarvelException.NetworkException.ResourceNotFound(
                    IllegalArgumentException(CHARACTER_DETAILS_NOT_FOUND_ERROR_MESSAGE)
                ),
                backAction = { finish() },
            )
        }
    }

    private companion object {
        const val CHARACTER_DETAILS_NOT_FOUND_ERROR_MESSAGE =
            "Character not found in CharacterDetailsActivity"
    }
}