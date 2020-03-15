package com.example.simplifiedinstagramstorymodule.core.repository.cloud.story

import com.example.simplifiedinstagramstorymodule.core.exception.Failure
import com.example.simplifiedinstagramstorymodule.core.functional.Either
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.response.StoryResponse

interface StoryRepository {
    fun getStoryList(): Either<Failure, StoryResponse>
}