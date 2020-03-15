package com.example.simplifiedinstagramstorymodule.core.repository.cloud.response

import com.example.simplifiedinstagramstorymodule.core.utilities.Constants.Companion.DEFAULT_STATUS_CODE
import com.google.gson.annotations.SerializedName


data class StoryResponse(
    @SerializedName("statusCode") val statusCode: Int = DEFAULT_STATUS_CODE,
    @SerializedName("message") val message: Message? = null
)

data class Message(
    @SerializedName("node") val profile: List<Profile>
)

data class Profile(
    @SerializedName("id") val id: String,
    @SerializedName("profile_pic_url") val profilePicUrl: String,
    @SerializedName("username") val username: String,
    @SerializedName("stories") val stories: List<Story>
)

data class Story(
    @SerializedName("id") val id: String,
    @SerializedName("src") val src: String,
    @SerializedName("createdAt") val createdAt: Int,
    @SerializedName("is_video") val isVideo: Boolean
)

