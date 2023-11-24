package com.kfcman.models

interface GameType {
	val notation: String
	val moves: Array<String>
	val gameID: Int
}