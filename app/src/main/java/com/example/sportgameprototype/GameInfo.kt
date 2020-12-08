package com.example.sportgameprototype

import java.util.*

class GameInfo (
    var gameID : String,
    var season : String,
    var gameDate: String,
    var awayTeam: String,
    var homeTeam : String,
    var stationName : String,
    var gameTime : String,
    var gDate : Date) {
    override fun toString() = "Game (gameID : $gameID)"
}