package paixao.leonardo.marvel.heroes.feature.core.utils.ktx

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectIn(
    scope: CoroutineScope,
    action: suspend (value: T) -> Unit
): Job =
    scope.launch {
        val collector = FlowCollector<T> { value -> action(value) }
        collect(collector)
    }
