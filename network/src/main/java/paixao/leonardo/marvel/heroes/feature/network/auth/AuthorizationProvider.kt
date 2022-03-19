package paixao.leonardo.marvel.heroes.feature.network.auth

import java.math.BigInteger
import java.security.MessageDigest
import java.util.Date

internal typealias EncryptedHash = String

class AuthorizationProvider(
    private val date: Date
) {
    operator fun invoke(): AuthorizationParams {
        val timeStamp = date.time.toString()
        val publicKey = "ddc3db57be25d16b77382ac34d9521a1"
        val privateKey = "5a443d0c8f238cc0560499750cfa6b362bb4c52a"
        val concatKeys = timeStamp + privateKey + publicKey
        val encryptedHash = doCrypt(concatKeys)

        return AuthorizationParams(
            publicKey = publicKey,
            hash = encryptedHash,
            timeStampAsString = timeStamp
        )
    }

    private fun doCrypt(input: String): EncryptedHash {
        val digest = MessageDigest.getInstance(ENCRYPT_ALG)
        val digestAsBiteArray = BigInteger(SIG_NUM, digest.digest(input.toByteArray()))
        val formattedHash =
            digestAsBiteArray
                .toString(STRING_RADIX)
                .padStart(HASH_LENGTH, STRING_PAD_CHAR)

        return formattedHash
    }

    private companion object {
        const val ENCRYPT_ALG = "MD5"
        const val SIG_NUM = 1
        const val STRING_RADIX = 16
        const val STRING_PAD_CHAR = '0'
        const val HASH_LENGTH = 32
    }
}
