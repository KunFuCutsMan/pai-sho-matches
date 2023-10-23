package com.kfcman

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun main() {
    val gameID = "116917"

    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("https://skudpaisho.com") {
        url {
            appendPathSegments("backend", "getGameNotationAndClock.php")
            parameters.append("q", gameID)
        }
    }

    println(response.status)
    println(response.bodyAsText())

    client.close()
}
