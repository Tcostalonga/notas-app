package tarsila.costalonga.notasapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*

@InstallIn(SingletonComponent::class)
@Module

object AlarmInjection {

    @Provides
    fun proverToday(): Calendar {
        return Calendar.getInstance()
    }

}