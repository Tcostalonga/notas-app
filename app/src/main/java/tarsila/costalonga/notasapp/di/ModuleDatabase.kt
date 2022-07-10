package tarsila.costalonga.notasapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tarsila.costalonga.notasapp.bd.NotasDao
import tarsila.costalonga.notasapp.bd.NotasRoom
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ModuleDatabase {

    @Provides
    @Singleton
    fun proverDatabase(@ApplicationContext context: Context): NotasRoom {
        return Room.databaseBuilder(
            context.applicationContext,
            NotasRoom::class.java,
            "notas_bd"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides

    fun proverDao(dtBase: NotasRoom): NotasDao {
        return dtBase.notasDao
    }
/*
    @Singleton
    @Provides
    fun provideDefaultDao(
        dtBase: NotasDao
    ) = NotasRepositorio(dtBase) as DefaultNotasRepositorio
*/
}
