package paixao.leonardo.marvel.heroes.feature.core.views.errorView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import paixao.leonardo.marvel.heroes.feature.core.databinding.ErrorViewBinding
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.utils.isVisible

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding
        get() = ErrorViewBinding.bind(this)

    fun renderError(
        marvelException: MarvelException,
        backAction: (() -> Unit)? = null,
        retryAction: (() -> Unit)? = null
    ) {
        val errorPresentation = ErrorPresentationMapper(marvelException = marvelException)

        setupView(errorPresentation = errorPresentation)
        setupBackButton(backAction = backAction)
        setupRetryAction(retryAction = retryAction)
    }

    private fun setupView(errorPresentation: ErrorPresentation) {
        val errorDrawable = AppCompatResources.getDrawable(context, errorPresentation.imgRes)
        binding.errorTitle.text = context.getString(errorPresentation.titleRes)
        binding.errorSubtitle.text = context.getString(errorPresentation.subtitleRes)
        binding.errorImageView.setImageDrawable(errorDrawable)
    }

    private fun setupBackButton(backAction: (() -> Unit)?) {
        binding.backButton.isVisible = backAction != null
        backAction?.let {
            binding.backButton.setOnClickListener {
                backAction()
            }
        }
    }

    private fun setupRetryAction(retryAction: (() -> Unit)?) {
        binding.retryButton.isVisible = retryAction != null
        retryAction?.let {
            binding.retryButton.setOnClickListener {
                retryAction()
            }
        }
    }
}
