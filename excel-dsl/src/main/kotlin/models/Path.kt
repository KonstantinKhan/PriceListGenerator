package models

@JvmInline
value class Path(private val path: String) {
    val value: String
        get() = path

    companion object {
        val NONE = Path(String())
    }
}