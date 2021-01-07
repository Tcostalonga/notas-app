package tarsila.costalonga.notasapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.*

@InstallIn(ApplicationComponent::class)
@Module

object AlarmInjection {

    @Provides
    fun proverToday(): Calendar {
        return Calendar.getInstance()
    }

}