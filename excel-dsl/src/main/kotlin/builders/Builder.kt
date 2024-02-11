package builders

interface Builder<T> {
    fun build(): T
}