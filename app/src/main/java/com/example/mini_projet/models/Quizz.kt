package com.example.mini_projet.models

data class Quizz(
    var id:String="",
    var title:String="",
    var questions:MutableMap<String,Question> =mutableMapOf(),

)