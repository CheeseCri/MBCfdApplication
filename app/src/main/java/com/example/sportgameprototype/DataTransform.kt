package com.example.sportgameprototype

import android.widget.ImageView

fun posTransform(posNumber : Int) : String{
    when(posNumber){
        1 -> return "투수"
        2 -> return "포수"
        3 -> return "1루수"
        4 -> return "2루수"
        5 -> return "3루수"
        6 -> return "유격수"
        7 -> return "좌익수"
        8 -> return "중견수"
        9 -> return "우익수"
        else -> return "?"
    }
}

fun changeImage(view : ImageView, teamName : String){
    when(teamName){
        "HH" -> view.setImageResource(R.drawable.logo_hh)
        "HT" -> view.setImageResource(R.drawable.logo_kia)
        "KT" -> view.setImageResource(R.drawable.logo_kt)
        "LG" -> view.setImageResource(R.drawable.logo_lg)
        "LT" -> view.setImageResource(R.drawable.logo_lt)
        "NC" -> view.setImageResource(R.drawable.logo_nc)
        "OB" -> view.setImageResource(R.drawable.logo_ob)
        "SK" -> view.setImageResource(R.drawable.logo_sk)
        "SS" -> view.setImageResource(R.drawable.logo_ss)
        "WO" -> view.setImageResource(R.drawable.logo_wo)
    }
}

fun changeTeam(teamID : String) : String {
    return when(teamID){
        "HH" -> "한화"
        "HT" -> "KIA"
        "LT" -> "롯데"
        "OB" -> "두산"
        "SS" -> "삼성"
        "WO" -> "키움"
        "KT", "LG", "NC", "SK" -> teamID
        else -> "오류"
    }
}