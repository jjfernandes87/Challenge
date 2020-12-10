package br.com.mouzinho.marvelapp.di

data class MarvelQueryParams(
    val publicApiKey: String,
    val timeStamp: String,
    val hash: String
) {

    companion object {
        const val PUBLIC_API_KEY_VALUE = "909954490eb16b54a3386a548d1aacb3"
        const val PRIVATE_API_KEY_VALUE = "2f72d188ec40d13456fc802e2fe325ed47548a7e"
    }
}