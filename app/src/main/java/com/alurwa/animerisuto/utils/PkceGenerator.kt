package com.alurwa.animerisuto.utils

import org.apache.commons.lang3.RandomStringUtils

/**
 * Created by Purwa Shadr Al 'urwa on 05/05/2021
 */

object PkceGenerator {
    private const val CODE_VERIFIER_STRING =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._~"

    fun generateVerifier(length: Int): String {
        return RandomStringUtils.random(length, CODE_VERIFIER_STRING)
    }
}
