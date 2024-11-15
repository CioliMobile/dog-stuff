package com.cioli.dogstuff.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cioli.dogstuff.RandomBreedImageActivity
import com.cioli.dogstuff.model.DogBreed
import com.cioli.dogstuff.network.DogApiInterface
import com.cioli.dogstuff.network.DogServiceInstance
import com.cioli.dogstuff.network.models.ListAllBreedsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// For UI State Tracking
sealed interface MainActivityUiState {
	data object Success: MainActivityUiState
	data object Error: MainActivityUiState
	data object Loading: MainActivityUiState
}

class MainActivityViewModel: ViewModel() {
	var mainActivityUiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
		private set
	private lateinit var dogApi: DogApiInterface
	var allBreeds by mutableStateOf<List<DogBreed>?>(emptyList())

	init {
		performSetup()
		populateData()
	}

	private fun performSetup() {
		dogApi = DogServiceInstance.getInstance().create(DogApiInterface::class.java)
	}

	private fun populateData() {
		mainActivityUiState = MainActivityUiState.Loading
		val call = dogApi.getListOfBreeds()
		call.enqueue(object : Callback<ListAllBreedsResponse> {
			override fun onResponse(p0: Call<ListAllBreedsResponse>, p1: Response<ListAllBreedsResponse>) {
				if (p1.isSuccessful && p1.body() != null) {
					Log.d("NETWORK:", "onResponse: SUCCESS")
					allBreeds = p1.body()?.getBreedList() ?: emptyList()
					mainActivityUiState = MainActivityUiState.Success
				} else {
					Log.d("NETWORK", "onResponse: Response is not successful")
				}
			}

			override fun onFailure(p0: Call<ListAllBreedsResponse>, p1: Throwable) {
				mainActivityUiState = MainActivityUiState.Error
				Log.d("NETWORK", "onFailure: Throwable - ${p1.message}\n${p1.printStackTrace()}")
			}
		})
	}

	fun breedSelected(breedValue: String, isSubBreed: Boolean, context: Context, subBreedValue: String? = null) {
		val intent = Intent(context, RandomBreedImageActivity::class.java)
		intent.putExtra(RandomBreedImageActivity.BREED_VALUE_KEY, breedValue)
		intent.putExtra(RandomBreedImageActivity.IS_SUBBREED_KEY, isSubBreed)
		if (isSubBreed) {
			intent.putExtra(RandomBreedImageActivity.SUBBREED_VALUE_KEY, subBreedValue)
		}
		context.startActivity(intent)
	}

	fun retryFetchBreedList() {
		popuL
	}
}