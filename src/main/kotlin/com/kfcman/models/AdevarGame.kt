package com.kfcman.models

class AdevarGame(override val notation: String, override val gameID: Int) : GameType {

	override val moves: Array<String>
		get() = emptyArray()

}