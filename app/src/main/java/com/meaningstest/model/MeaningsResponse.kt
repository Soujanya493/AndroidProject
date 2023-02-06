package com.meaningstest.model

data class MeaningsResponse(
    val lfs: List<Lf>,
    val sf: String = ""
) {
    data class Lf(
        val freq: Int,
        val lf: String,
        val since: Int,
        val vars: List<Var> = emptyList()
    ) {
        data class Var(
            val freq: Int,
            val lf: String,
            val since: Int
        )
    }
}