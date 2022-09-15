package br.com.testechucknorris.model

data class JokeModel (
    val icon_url: String,
    val id: String,
    val url: String,
    val value: String
    )

data class SearchModel(
    val total: Int,
    val result: List<JokeModel>
)
