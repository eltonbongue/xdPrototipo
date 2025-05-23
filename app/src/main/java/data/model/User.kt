package data.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val photoPath: String? = null
)
