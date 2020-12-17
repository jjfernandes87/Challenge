package com.manoelsrs.marvelchallenge.repository.remote.characters.producers

import org.junit.Assert.assertEquals
import org.junit.Test

class MD5HashTest {

    private val md5Hash = MD5Hash()

    @Test
    fun `should produce MD5 hash`() {
        val timestamp = "1234567890"
        val publicKey = "publicKey"
        val privateKey = "privateKey"

        val expected = "d91c1876a7d2e41b0e247d13252591c6"
        val answer = md5Hash.produce(timestamp, publicKey, privateKey)

        assertEquals(expected, answer)
    }
}