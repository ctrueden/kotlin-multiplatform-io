package platform


actual fun <R> synchronize(lock: Any, block: () -> R): R = synchronized(lock, block)