package com.twitter.challenge.repo

import java.lang.Exception

data class ServerException(val code: Int, val error: String) : Exception()