package com.brhmty.dailytopnews.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiServiceTest {
    private lateinit var newsApiService: NewsApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        newsApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
    @Test
    fun receivedHeadlines(){
        runBlocking {
            enqueeMockResponse("newsresponse.json")
            val responseBody = newsApiService.getTopHeadlines(country = "us", pageSize = 1).body()
            //val request = mockWebServer.takeRequest()
            assertThat(responseBody).isNotNull()
        }
    }

    @Test
    fun contentCheck(){
        runBlocking {
            enqueeMockResponse("newsresponse.json")
            val responseBody = newsApiService.getTopHeadlines(country = "us", pageSize = 1).body()
            val articleList = responseBody!!.articles
            val article = articleList[0]
            assertThat(article.author).isEqualTo("Mike Snider, USA TODAY")
            assertThat(article.url).isEqualTo("https://www.usatoday.com/story/money/2022/01/29/spotify-joni-mitchell-remove-music-neil-young-vaccine-misinformation/9267962002/")
        }
    }

    private fun enqueeMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}