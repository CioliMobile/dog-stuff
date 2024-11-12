package com.cioli.dogstuff.network.models

class BreedImageResponse(private val message: String, private val status: String) {
	fun getImageUrlString(): String {
		return message
	}
}
