package com.cioli.dogstuff.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DogServiceInstance {
	private const val BASE_URL = "https://dog.ceo/api/"

	fun getInstance(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
}