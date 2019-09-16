package com.manoelsrs.marvelchallenge.repository.remote.characters.producers

import java.math.BigInteger
import java.security.MessageDigest


class MD5Hash {

    fun produce(timestamp: String, publicKey: String, privateKey: String): String {
        val stringToHash = timestamp + privateKey + publicKey
        val digest = MessageDigest.getInstance("MD5")
        digest.update(stringToHash.toByteArray())
        return BigInteger(1, digest.digest()).toString(16).padStart(32, '0')
    }
}