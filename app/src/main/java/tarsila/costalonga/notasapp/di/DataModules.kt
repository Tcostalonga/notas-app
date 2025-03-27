package tarsila.costalonga.notasapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import tarsila.costalonga.notasapp.data.local.NotasDao
import tarsila.costalonga.notasapp.data.local.NotasRoom
import tarsila.costalonga.notasapp.data.repository.NoteDataRepository
import tarsila.costalonga.notasapp.data.repository.NoteRepository

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): NotasRoom {
        return Room.databaseBuilder(
            context.applicationContext,
            NotasRoom::class.java,
            "notas_bd",
        )
            .fallbackToDestructiveMigration()
            .addMigrations(NotasRoom.MIGRATION_2_3)
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(dtBase: NotasRoom): NotasDao {
        return dtBase.notasDao
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        repository: NoteDataRepository,
    ): NoteRepository
}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences("preferences_nota_anota", Context.MODE_PRIVATE)
    }
}
