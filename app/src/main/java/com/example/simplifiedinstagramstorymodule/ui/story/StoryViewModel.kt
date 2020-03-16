package com.example.simplifiedinstagramstorymodule.ui.story

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.simplifiedinstagramstorymodule.core.exception.Failure
import com.example.simplifiedinstagramstorymodule.core.interactor.UseCase
import com.example.simplifiedinstagramstorymodule.core.platform.BaseViewModel
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.response.StoryResponse
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.story.StoryUseCase
import javax.inject.Inject

class StoryViewModel
@Inject constructor(
    val storyUseCase: StoryUseCase
) : BaseViewModel() {

    val storyResponseLiveData: MutableLiveData<StoryResponse> = MutableLiveData()

    init {
        getStoryList()
    }

    private fun getStoryList() {
        storyUseCase(UseCase.None()) {
            it.either(::handleFailureState, ::onStoryListResponse)
        }
    }

    private fun onStoryListResponse(storyResponse: StoryResponse) {
        Log.e("EM", storyResponse.toString())
        storyResponseLiveData.postValue(storyResponse)

    }

    private fun handleFailureState(failure: Failure) {
        this.failure.value = failure
        Log.e("EM", failure.toString())
    }

}
