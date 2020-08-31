package tarsila.costalonga.notasapp

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@HiltAndroidTest
class EstatisticasVMTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repositorio: NotasRepositorio


    @Before
    fun init (){
        hiltRule.inject()
    }

/*    @Test
    fun*/


}