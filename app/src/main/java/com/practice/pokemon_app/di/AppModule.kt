package com.practice.pokemon_app.di

import android.content.Context
import androidx.room.Room
import com.practice.pokemon_app.data.local.PokemonDao
import com.practice.pokemon_app.data.local.PokemonDatabase
import com.practice.pokemon_app.data.remote.api.PokeApi
import com.practice.pokemon_app.data.repository.PokemonRepository
import com.practice.pokemon_app.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ): PokemonDatabase{
        return Room.databaseBuilder(
            app,
            PokemonDatabase::class.java,
            "pokomen_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(
        database: PokemonDatabase
    ): PokemonDao{
        return database.getPokemonDao()
    }

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi,
        pokemonDao: PokemonDao
    ): PokemonRepository{
        return PokemonRepository(api, pokemonDao)
    }

}