package com.cioli.dogstuff.model

class DogBreed(
	val breedName: String,
	val displayBreedName: String,
	subBreeds: List<String> = emptyList(),
	val subBreedName: String? = null
) {
	val hasSubBreeds: Boolean = subBreeds.isNotEmpty()
	val isSubBreed = subBreedName != null
}