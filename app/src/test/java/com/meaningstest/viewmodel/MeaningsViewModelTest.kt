package com.meaningstest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.meaningstest.api.MeaningsApi
import com.meaningstest.di.NetworkRepository
import com.meaningstest.model.MeaningsResponse
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MeaningsViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    lateinit var viewModel: MeaningsViewModel
    lateinit var networkRepository: NetworkRepository

    private val mockResponse = listOf(
        MeaningsResponse(
            listOf(
                MeaningsResponse.Lf(10, "testing", 1996)
            )
        )
    )

    @Mock
    lateinit var meaningsApi: MeaningsApi

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        networkRepository = NetworkRepository(meaningsApi)
        viewModel = MeaningsViewModel(networkRepository)
    }

    @Test
    fun getMeaningsApiSuccessTest() {

        runBlocking {
            Mockito.`when`(networkRepository.getMeanings("test"))
                .thenReturn(
                    Response.success(
                        mockResponse
                    )
                )
            viewModel.getMeaningsList("test")
            val result = viewModel.meaningsList.value
            assertEquals(
                mockResponse,
                result?.data
            )
        }

    }

    @Test
    fun `empty meanings list test`() {
        runBlocking {
            Mockito.`when`(networkRepository.getMeanings(""))
                .thenReturn(Response.success(listOf<MeaningsResponse>()))
            viewModel.getMeaningsList("")
            val result = viewModel.meaningsList.value
            Assert.assertTrue(result?.data?.isEmpty() == true)
        }
    }

    @Test
    fun `meanings list is null test`() {
        runBlocking {
            Mockito.`when`(networkRepository.getMeanings(""))
                .thenReturn(null)
            viewModel.getMeaningsList("")
            val result = viewModel.meaningsList.value
            Assert.assertNull(result?.data)
        }
    }
}