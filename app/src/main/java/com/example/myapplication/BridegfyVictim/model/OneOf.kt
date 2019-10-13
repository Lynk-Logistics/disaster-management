package com.example.myapplication.BridegfyVictim.model

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [OneOf] are either an instance of [Success] or [Failure].
 *
 * @see Success
 * @see Failure
 */
sealed class OneOf<out S, out F : Failure> {
    /** * Represents the success side of [OneOf] class which by convention is a "Failure".
     * @param S represents the data type to be returned by the successful response
     * @param dataState represents the type of data loaded into the success response,
     * [DataState.CACHED_DATA] represents cached data and
     * [DataState.FRESH_DATA] represents a newly loaded data
     * */
    data class Success<S>(
        val a: S,
        val dataState: DataState
    ) : OneOf<S, Nothing>() {
        enum class DataState {
            FRESH_DATA, CACHED_DATA
        }
    }

    /** * Represents the failure side of [OneOf] class which by convention is a "Failure". */
    data class Failure<F : com.example.myapplication.BridegfyVictim.model.Failure>(val b: F) : OneOf<Nothing, F>()

    val isFailure get() = this is Failure<F>
    val isSuccess get() = this is Success<S>

    fun <S> success(a: S, dataState: Success.DataState) = Success(a, dataState)
    fun <F : com.example.myapplication.BridegfyVictim.model.Failure> failure(b: F) = Failure(b)

    suspend fun oneOf(fnS: suspend (S) -> Any, fnF: suspend (F) -> Any): Any =
        when (this) {
            is Success -> fnS(a)
            is Failure -> fnF(b)
        }
}

fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}