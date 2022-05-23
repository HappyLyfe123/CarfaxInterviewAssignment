package com.carfax.library_network

//sealed class Async<T>(val data: T? = null, val message: String? = null) {
//    class Success<T>(data: T?) : Async<T>(data)
//    class Error<T>(message: String, data: T? = null) : Async<T>(data, message)
//    class Loading<T>(val isLoading: Boolean = true) : Async<T>(null)
//}

sealed class Async<out T>(val isComplete: Boolean, val isLoading: Boolean, val isError: Boolean, private val value: T?) {

    /**
     * Returns the value or null.
     *
     * Success always have a value. Loading and Fail can also return a value which is useful for
     * pagination or progressive data loading.
     *
     * Can be invoked as an operator like: `yourProp()`
     */
    open operator fun invoke(): T? = value
}

object Uninitialized : Async<Nothing>(isComplete = false, isLoading = false, isError = false, value = null), Incomplete

data class Loading<out T>(private val value: T? = null) : Async<T>(isComplete = false, isLoading = true, isError = false, value = value),
    Incomplete

data class Success<out T>(private val value: T) : Async<T>(isComplete = true, isLoading = false, isError = false, value = value) {

    override operator fun invoke(): T = value
}

data class Fail<out T>(val error: Throwable, private val value: T? = null) : Async<T>(isComplete = true, isLoading = true, isError = true,
value = value) {
    override fun equals(other: Any?): Boolean {
        if (other !is Fail<*>) return false

        val otherError = other.error
        return error::class == otherError::class &&
                error.message == otherError.message &&
                error.stackTrace.firstOrNull() == otherError.stackTrace.firstOrNull()
    }

    override fun hashCode(): Int = arrayOf(error::class, error.message, error.stackTrace.firstOrNull()).contentHashCode()
}

/**
 * Helper interface for using Async in a when clause for handling both Uninitialized and Loading.
 *
 * With this, you can do:
 * when (data) {
 *     is Incomplete -> Unit
 *     is Success    -> Unit
 *     is Fail       -> Unit
 * }
 */
interface Incomplete