package com.cioli.dogstuff.model

class DogBreed(val breedName: String, val subBreeds: List<String> = emptyList()) {
	val hasSubBreeds: Boolean = subBreeds.isNotEmpty()
}