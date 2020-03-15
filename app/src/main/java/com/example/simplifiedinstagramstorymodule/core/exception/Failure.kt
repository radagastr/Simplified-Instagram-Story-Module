package com.example.simplifiedinstagramstorymodule.core.exception

/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {
    class NetworkConnection: Failure()
    class ServerError(val errorMessage: String): Failure()
}