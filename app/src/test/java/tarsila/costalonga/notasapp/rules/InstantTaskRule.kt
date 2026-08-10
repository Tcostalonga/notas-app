@file:OptIn(ExperimentalCoroutinesApi::class)

package tarsila.costalonga.notasapp.rules

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import tarsila.costalonga.notasapp.DispatcherProvider
import tarsila.costalonga.notasapp.DispatcherProviderInterface

class InstantTaskRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher(),
) : DispatcherProviderInterface, TestWatcher() {

    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val mainImmediate: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
        DispatcherProvider.forceDispatcher(testDispatcher)
    }

    override fun finished(description: Description?) {
        DispatcherProvider.reset()
        Dispatchers.resetMain()
        super.finished(description)
    }
}
