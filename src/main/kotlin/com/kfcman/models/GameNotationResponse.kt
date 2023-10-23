package com.kfcman.models

/**
 * @property clock A possible timestamp of a game
 * @property notation The notation of said match. Doesn't tell you which kind of game it is.
 */
data class GameNotationResponse(
	val clock: String?,
	val notation: String
)
