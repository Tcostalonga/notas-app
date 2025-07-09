package tarsila.costalonga.notasapp

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProviderInterface {
    val main: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}

object DispatcherProvider : DispatcherProviderInterface {
    private var forcedDispatcher: CoroutineDispatcher? = null

    override val main: CoroutineDispatcher get() = forcedDispatcher ?: Dispatchers.Main
    override val mainImmediate: CoroutineDispatcher get() = forcedDispatcher ?: Dispatchers.Main
    override val io: CoroutineDispatcher get() = forcedDispatcher ?: Dispatchers.IO
    override val default: CoroutineDispatcher get() = forcedDispatcher ?: Dispatchers.Default

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun forceDispatcher(dispatcher: CoroutineDispatcher) {
        forcedDispatcher = dispatcher
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun reset() {
        forcedDispatcher = null
    }
}


