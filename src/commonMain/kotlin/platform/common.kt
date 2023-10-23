package platform

open class FormatException(message: String, cause: Throwable? = null) : Exception(message, cause)


expect fun <R> synchronize(lock: Any, block: () -> R): R


/** Denotes types that will be processed by the Kotlin Assignment plugin */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class KotlinAssignmentOverloadTarget
