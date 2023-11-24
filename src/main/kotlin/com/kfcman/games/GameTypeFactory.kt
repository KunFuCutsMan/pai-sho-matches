package com.kfcman.games

import com.kfcman.models.*

private infix fun Boolean.then(f: () -> Unit) = if (this) {
	f()
} else {
}

class GameTypeFactory {

	// Skud Pai Sho Matches always start with the host choosing their accent tiles
	// This may include Ancient Expansion types, so I'm adding those too
	// Ej: 0H.R,K,W,B
	private val skudRGX = Regex("^(0H.(R,|K,|W,|B,|M,|P,|T,)+)")

	// Vagabond matches always start with Host, having to place *something* on the board,
	// At a certain position. Ej: 0H.C(-5,-5)
	private val vagabondRGX = Regex("^(0H\\.\\w\\(-?\\d,)")

	// Surprisingly simple, on Adevar host on turn 0H chooses a hidden tile. We only care for the beginning
	// Ej. 0H.cHT:BlackOrchid;0G.cHT:WhiteRose;1H.HLilac(4,2);1G.(-2,-7)-(0,-4); ...
	private val adevarRGX = Regex("^(0H\\.cHT:)")

	// Fire matches always start with guest planting a flower (i.e. R5, W)
	// Placing it a certain position. Ej: 1G.R5(-4,0)
	private val fireRGX = Regex("^(1G\\.(\\w\\d?)\\((-?\\d),(-?\\d)\\))")

	// Gingseng notation is actually an array, so we need to check for "[",
	// and to be sure check the "moveNum" of host's empty move
	// Ej. [{"moveNum":0,"player":"HOST","moveType":"--","promptTargetData":{}},{"moveNum":1,"player":"GUEST" ...
	private val gingsengRGX = Regex("^\\[\\{\"moveNum\":\\d,")

	fun getGameType(game: GameNotationResponse, gameID: Int): GameType {

		var thingy: GameType? = null

		game.notation.apply {
			contains(skudRGX).then {
				// println("Skud Match")
				thingy = SkudGame(this, gameID)
			}
			contains(vagabondRGX).then {
				// println("Vagabond Match")
				thingy = VagavondGame(this, gameID)
			}
			contains(adevarRGX).then {
				// println("Adevar Match")
				thingy = AdevarGame(this, gameID)
			}
			contains(fireRGX).then {
				// println("Fire Match")
				thingy = FireGame(this, gameID)
			}
			contains(gingsengRGX).then {
				// println("Gingseng")
				thingy = GingsengGame(this, gameID)
			}
		}

		if (thingy == null)
			thingy = UnknownGame(game.notation, gameID)

		return thingy!!
	}


}