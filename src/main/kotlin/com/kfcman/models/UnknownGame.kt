package com.kfcman.models

class UnknownGame(override val notation: String, override val gameID: Int) : GameType {

	override val moves: Array<String> = emptyArray()
}