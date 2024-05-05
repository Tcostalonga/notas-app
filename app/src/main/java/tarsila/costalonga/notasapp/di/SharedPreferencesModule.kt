package tarsila.costalonga.notasapp.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences("preferences_nota_anota", Context.MODE_PRIVATE)
    }
}
