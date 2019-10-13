package com.example.myapplication.BridegfyVictim.model

sealed class Failure {

    /**
     * class : UnknownFailure
     * this type of failure is used when the cause of error is unknown
     * and this failure also represents the generic failure
     * */
    object UnKnownFailure : Failure()

    /**
     * The request was valid, but the server is refusing action.
     * The user might not have the necessary permissions for a resource,
     * or may need an account of some sort. This code is also typically used
     * if the request provided authentication via the WWW-Authenticate header field, but the server did not accept that authentication.
     * */
    object ForbiddenFailure : Failure()

    /**
    The server timed out waiting for the request. According to HTTP specifications: "The client did not produce a request
    within the time that the server was prepared to wait. The client MAY repeat the request without modifications at any later time.
     */
    object RequestTimeoutFailure : Failure()

    /**
    The requested resource could not be found but may be available in the future. Subsequent requests by the client are permissible.
     */
    object NotFoundFailure : Failure()

    /**
     * This failure represents a generic network failure. for eg: this failure can be used in
     * case of no internet connection
     */

    object NetworkFailure : Failure()

    object DatabaseFailure : Failure()

    /**
     * use this failure if it is not possible to fetch location. if both fetch from network and database is unsuccessful
     * */


    sealed class LocationFailure : Failure() {

        object LocationPermissionDeniedFailure : LocationFailure()

        object LocationNotEnabledFailure : LocationFailure()

        object NoSavedLocationFailure : LocationFailure()

        object UnknownLocationFailure : LocationFailure()

    }
}