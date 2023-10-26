package platform


/**
 * FormatException is the exception thrown when something goes wrong performing
 * a file format operation.
 */
open class FormatException : Exception {
    constructor() : super()
    constructor(s: String) : super(s)
    constructor(s: String, cause: Throwable) : super(s, cause)
    constructor(cause: Throwable) : super(cause)
}


expect fun <R> synchronize(lock: Any, block: () -> R): R


/** Denotes types that will be processed by the Kotlin Assignment plugin */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class KotlinAssignmentOverloadTarget
