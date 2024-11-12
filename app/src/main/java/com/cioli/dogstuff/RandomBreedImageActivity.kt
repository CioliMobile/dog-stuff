package com.cioli.dogstuff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil3.compose.AsyncImage
import com.cioli.dogstuff.ui.theme.DogStuffTheme
import com.cioli.dogstuff.viewmodel.RandomBreedImageUiState
import com.cioli.dogstuff.viewmodel.RandomBreedImageViewModel
import com.cioli.dogstuff.viewmodel.RandomBreedImageViewModelFactory

class RandomBreedImageActivity : ComponentActivity() {

	companion object {
		const val BREED_VALUE_KEY = "BREED_VALUE_KEY"
		const val IS_SUBBREED_KEY = "IS_SUBBREED_KEY"
		const val SUBBREED_VALUE_KEY = "SUBBREED_VALUE_KEY"
	}

	private lateinit var viewModel: RandomBreedImageViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()

		val breed = intent.getStringExtra(BREED_VALUE_KEY) ?: ""
		val isSubBreed = intent.getBooleanExtra(IS_SUBBREED_KEY, false)
		val subBreed = intent.getStringExtra(SUBBREED_VALUE_KEY)
		viewModel = ViewModelProvider.create(
			this, RandomBreedImageViewModelFactory(breed, isSubBreed, subBreed))[RandomBreedImageViewModel::class.java]

		setContent {
			DogStuffTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					when (viewModel.randomBreedImageUiState) {
						RandomBreedImageUiState.Loading -> LoadingView()
						RandomBreedImageUiState.Success -> MainView(Modifier.padding(innerPadding))
						RandomBreedImageUiState.Error -> ErrorView()
					}
				}
			}
		}
	}

	@Composable
	fun MainView(modifier: Modifier) {
		Column(
			modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 24.dp),
			verticalArrangement = Arrangement.Center
		) {
			AsyncImage(model = viewModel.breedImageUrl, contentDescription = "dog")
		}
	}

	@Composable
	fun LoadingView() {

	}

	@Composable
	fun ErrorView() {

	}
}