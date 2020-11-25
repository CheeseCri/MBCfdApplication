package com.example.sportgameprototype

class GameInfo (
    var gameID : String,
    var season : String,
    var gameDate: String,
    var awayTeam: String,
var homeTeam : String,
var stationName : String,
var gameTime : String) {
    override fun toString() = "Game (gameID : $gameID)"
}