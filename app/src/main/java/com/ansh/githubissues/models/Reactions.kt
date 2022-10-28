package com.ansh.githubissues.models

data class Reactions(
    val plusOne: Int,
    val minusOne: Int,
    val confused: Int,
    val eyes: Int,
    val heart: Int,
    val hooray: Int,
    val laugh: Int,
    val rocket: Int,
    val total_count: Int,
    val url: String
)