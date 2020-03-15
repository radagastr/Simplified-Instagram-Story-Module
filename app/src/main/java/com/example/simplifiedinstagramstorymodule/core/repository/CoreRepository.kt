package com.example.simplifiedinstagramstorymodule.core.repository

import com.example.simplifiedinstagramstorymodule.core.exception.Failure
import com.example.simplifiedinstagramstorymodule.core.functional.Either
import com.example.simplifiedinstagramstorymodule.core.platform.NetworkHandler
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.NetworkService
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.response.StoryResponse
import com.example.simplifiedinstagramstorymodule.core.repository.cloud.story.StoryRepository
import com.google.gson.Gson
import retrofit2.Call
import javax.inject.Inject

interface CoreRepository : StoryRepository {

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: NetworkService
    ) : CoreRepository {

        override fun getStoryList(): Either<Failure, StoryResponse> {
            return when (networkHandler.isConnected) {
                true -> requestWithoutBaseResponse(service.getStoryList(), { it }, StoryResponse())
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        private inline fun <reified T, R> requestWithoutBaseResponse(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> {
                        Either.Right(transform((response.body() ?: default)))
                    }
                    false -> {
                        Either.Left(Failure.ServerError("Server Hatası!"))
                    }
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError("Server Hatası!"))
            }
        }
        /*

        private fun <T, R> requestWithBaseResponseList(
            call: Call<BaseResponseModel<List<T>>>,
            transform: (List<T>) -> R,
            default: List<T>
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> {
                        Either.Right(transform((response.body()?.data ?: default)))
                    }
                    false -> {
                        val errorMessage = "Server Hatası!"
                        Either.Left(Failure.ServerError(errorMessage))
                    }
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError("Server Hatası!"))
            }
        }

         */
    }


}