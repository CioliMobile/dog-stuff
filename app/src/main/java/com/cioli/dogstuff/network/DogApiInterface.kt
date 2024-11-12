package com.cioli.dogstuff.network

import com.cioli.dogstuff.network.models.BreedImageResponse
import com.cioli.dogstuff.network.models.ListAllBreedsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiInterface {
	@GET("breeds/list/all/")
	fun getListOfBreeds(): Call<ListAllBreedsResponse>

	@GET("breed/{breed}/images/random/")
	fun getRandomImageForBreed(@Path("breed") breed: String): Call<BreedImageResponse>

	@GET("breed/{breed}/{subbreed}/images/random")
	fun getRandomImageForSubBreed(@Path("breed") breed: String, @Path("subbreed") subBreed: String) : Call<BreedImageResponse>
}