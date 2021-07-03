package com.example.deloitte_flickr_search.dependancies

import com.example.deloitte_flickr_search.networking.FlickrApi
import com.example.deloitte_flickr_search.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePhotoRepository(
        flickrApi: FlickrApi,
    ): PhotoRepository {
        return PhotoRepository(flickrApi)
    }
}