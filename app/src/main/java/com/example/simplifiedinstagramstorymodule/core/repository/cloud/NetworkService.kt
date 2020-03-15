package com.example.simplifiedinstagramstorymodule.core.repository.cloud

import com.example.simplifiedinstagramstorymodule.core.repository.cloud.story.StoryApi
import com.example.simplifiedinstagramstorymodule.core.utilities.Constants
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class NetworkService @Inject constructor(
    @Named(Constants.REGULAR_RETROFIT) retrofit: Retrofit
) : StoryApi {

    private val storyApi by lazy { retrofit.create(StoryApi::class.java) }

    override fun getStoryList() = storyApi.getStoryList()
}