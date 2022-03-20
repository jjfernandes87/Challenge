package paixao.leonardo.marvel.heroes.feature.network.auth

data class AuthorizationParams(
    val publicKey: String,
    val hash: String,
    val timeStampAsString: String,
)
