package com.kfcman

import com.kfcman.coroutines.getGameFromOnline
import com.kfcman.games.GameTypeFactory
import com.kfcman.models.GameType
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.system.measureTimeMillis

suspend fun main() {

    val client = HttpClient(CIO)
    val gameFactory = GameTypeFactory()

    val deferredValues = mutableListOf<GameType>()

    val time = measureTimeMillis {
        coroutineScope {
            repeat(30) { index ->
                async {
                    val deferredGame = getGameFromOnline(index + 1, client)
                    val parsedGame = gameFactory.getGameType(deferredGame, index + 1)
                    deferredValues.add(parsedGame)
                }
            }
        }

        client.close()
    }
    println("Time: $time ms")
    deferredValues
        .sortedBy { it.gameID }
        .forEach { game ->
            println("${game.gameID}, ${game.notation}")
        }
}
