package com.kfcman.models

class SkudGame(override val notation: String, override val gameID: Int) : GameType {

	override val moves: Array<String>
		get() = emptyArray()

}