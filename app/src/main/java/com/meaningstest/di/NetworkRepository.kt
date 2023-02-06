package com.meaningstest.di

import com.meaningstest.api.MeaningsApi
import com.meaningstest.model.MeaningsResponse
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val usersDataApi: MeaningsApi
) {
    suspend fun getMeanings(input: String): Response<List<MeaningsResponse>> {
        return usersDataApi.getMeanings(input)
    }
}