package com.alurwa.animerisuto.utils

enum class StatusUserAnimeList(val code: String?) {
    ALL(null),
    WATCHING("watching"),
    COMPLETED("completed"),
    ON_HOLD("on_hold"),
    DROPPED("dropped"),
    PLAN_TO_WATCH("plan_to_watch")
}