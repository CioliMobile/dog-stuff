package com.cioli.dogstuff.network.models

import com.cioli.dogstuff.model.DogBreed

class ListAllBreedsResponse(
	private val message: Map<String, ArrayList<String>>, private val status: String
) {
	fun getBreedList(): List<DogBreed> {
		val breedList = ArrayList<DogBreed>()
		message.keys.forEach { k ->
			val subBreeds = message[k]
			val mainBreed = DogBreed(k, subBreeds ?: arrayListOf())
			breedList.add(mainBreed)
			if (subBreeds != null) {
				for (sb in subBreeds) {
					breedList.add(DogBreed("$k, $sb"))
				}
			}
		}

		return breedList
	}
}