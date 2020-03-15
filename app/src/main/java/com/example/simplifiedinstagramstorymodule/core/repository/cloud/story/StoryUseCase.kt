package com.example.simplifiedinstagramstorymodule.core.repository.cloud.story

import com.example.simplifiedinstagramstorymodule.core.interactor.UseCase
import com.example.simplifiedinstagramstorymodule.core.repository.CoreRepository
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.response.StoryResponse
import javax.inject.Inject

class StoryUseCase
@Inject constructor(private val coreRepository: CoreRepository) :
    UseCase<StoryResponse, UseCase.None>() {
    override suspend fun run(params: None) = coreRepository.getStoryList()


}