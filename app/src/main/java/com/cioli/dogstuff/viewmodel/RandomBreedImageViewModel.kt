package com.cioli.dogstuff.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cioli.dogstuff.network.DogApiInterface
import com.cioli.dogstuff.network.DogServiceInstance
import com.cioli.dogstuff.network.models.BreedImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// For UI State Tracking
sealed interface RandomBreedImageUiState {
	data object Success: RandomBreedImageUiState
	data object Error: RandomBreedImageUiState
	data object Loading: RandomBreedImageUiState
}

class RandomBreedImageViewModel(private var breedString: String): ViewModel() {
	var randomBreedImageUiState: RandomBreedImageUiState by
		mutableStateOf(RandomBreedImageUiState.Loading)
		private set
	private lateinit var dogApi: DogApiInterface
	var breedImageUrl: String? by mutableStateOf("")

	init {
		performSetup()
		populateData()
	}

	private fun performSetup() {
		dogApi = DogServiceInstance.getInstance().create(DogApiInterface::class.java)
	}

	private fun populateData() {
		randomBreedImageUiState = RandomBreedImageUiState.Loading
		val call = dogApi.getRandomImageFor(breedString)
		call.enqueue(object: Callback<BreedImageResponse> {
			override fun onResponse(p0: Call<BreedImageResponse>, p1: Response<BreedImageResponse>) {
				if (p1.isSuccessful && p1.body() != null) {
					breedImageUrl = p1.body()?.getImageUrlString()
					randomBreedImageUiState = RandomBreedImageUiState.Success
				} else {
					randomBreedImageUiState = RandomBreedImageUiState.Error
				}
			}

			override fun onFailure(p0: Call<BreedImageResponse>, p1: Throwable) {
				Log.d("NETWORK", "FAILED CALL TO BREED IMAGE")
			}
		})
	}
}

class RandomBreedImageViewModelFactory(private var breed: String) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return RandomBreedImageViewModel(breed) as T
	}
}