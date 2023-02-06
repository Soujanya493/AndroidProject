package com.meaningstest.api

import com.meaningstest.model.MeaningsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface MeaningsApi {
    @GET("software/acromine/dictionary.py")
    suspend fun getMeanings(@Query("sf") sf: String): Response<List<MeaningsResponse>>
}