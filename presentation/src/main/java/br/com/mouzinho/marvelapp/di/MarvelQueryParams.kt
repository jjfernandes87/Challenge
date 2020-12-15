package br.com.mouzinho.marvelapp.di

data class MarvelQueryParams(
    val publicApiKey: String,
    val timeStamp: String,
    val hash: String
) {

    companion object {
        const val PUBLIC_API_KEY_VALUE = "c77f2f149122efe47fb9c00a378bd7cc"
        const val PRIVATE_API_KEY_VALUE = "1647fd9473668b369241dff5a628314b813c0b47"
    }
}