package com.example.simplifiedinstagramstorymodule.core.repository.cloud.story


import com.example.simplifiedinstagramstorymodule.core.repository.cloud.response.StoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface StoryApi {
    companion object {
        private const val GET_STORY = "/prod"
    }

    @GET(GET_STORY)
    fun getStoryList(): Call<StoryResponse>
}