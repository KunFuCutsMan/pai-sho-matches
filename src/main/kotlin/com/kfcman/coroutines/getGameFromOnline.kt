package com.kfcman.coroutines

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.kfcman.models.GameNotationResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

private const val URL_STRING = "https://skudpaisho.com"
private val gson = Gson()

suspend fun getGameFromOnline(gameID: Int, client: HttpClient): GameNotationResponse {

	val res = client.get(URL_STRING) {
		url {
			appendPathSegments("backend", "getGameNotationAndClock.php")
			parameters.append("q", "$gameID")
		}
	}

	// println("Status of ID $gameID: ${res.status}")

	var notation = GameNotationResponse(
		notation = "",
		clock = "",
	)
	try {
		notation = gson.fromJson(res.bodyAsText(), GameNotationResponse::class.java)
		// println("Notation of ID $gameID: ${notation.notation}")
	} catch (e: JsonSyntaxException) {
		// println("Error of ID $gameID: ${e.message}, got ${res.bodyAsText().trim()}")

	}

	return notation
}