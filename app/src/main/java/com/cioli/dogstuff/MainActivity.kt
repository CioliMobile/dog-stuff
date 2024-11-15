package com.cioli.dogstuff

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.cioli.dogstuff.model.DogBreed
import com.cioli.dogstuff.ui.theme.DogStuffTheme
import com.cioli.dogstuff.viewmodel.MainActivityUiState
import com.cioli.dogstuff.viewmodel.MainActivityViewModel

class MainActivity : ComponentActivity() {

	private lateinit var viewModel: MainActivityViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()

		viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

		setContent {
			DogStuffTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
					when (viewModel.mainActivityUiState) {
						MainActivityUiState.Loading -> LoadingView()
						MainActivityUiState.Error -> ErrorView()
						MainActivityUiState.Success -> MainView()
					}
				}
			}
		}
	}

	@Composable
	fun MainView() {
		LazyColumn {
			items(viewModel.allBreeds!!.size) { index ->
				val breed = viewModel.allBreeds!![index]
				BreedRow(breed)
			}
		}
	}

	@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
	@Preview(name="Light Mode", showBackground = true)
	@Composable
	fun BreedRow(@PreviewParameter(SampleDogBreedProvider::class) breed: DogBreed) {
		Card(
			modifier = Modifier.padding(8.dp).fillMaxWidth().clickable {
				viewModel.breedSelected(breed.breedName, breed.isSubBreed, this, breed.subBreedName)
			},
			elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
			) {
			Column(
				modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 24.dp),
				verticalArrangement = Arrangement.Center
			) {
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(
						breed.displayBreedName.capitalizeWords(),
						fontFamily = FontFamily.Serif,
						fontWeight = FontWeight.SemiBold
					)
					Spacer(Modifier.weight(1f))
					Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, contentDescription = "Right Arrow")
				}
			}
		}
	}

	@Preview("Light Mode", showBackground = true)
	@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
	@Composable
	fun LoadingView() {
		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			CircularProgressIndicator(
				modifier = Modifier.padding(bottom = 8.dp)
			)
			Text(
				text = "Loading Breed List"
			)
		}
	}

	@Preview("Light Mode",showBackground = true)
	@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
	@Composable
	fun ErrorView() {
		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text("Error Loading Breeds :(",
				fontSize = 40.sp,
				modifier = Modifier.padding(8.dp),
				textAlign = TextAlign.Center
			)
			Button(onClick = {
				viewModel.retryFetchBreedList()
			}) {
				Text("Retry")
			}
		}
	}
}

class SampleDogBreedProvider: PreviewParameterProvider<DogBreed> {
	override val values: Sequence<DogBreed> = sequenceOf(
		DogBreed("snoop dogg", "snoop dogg"),
		DogBreed("glorious dog breed", "glorious dog breed")
	)
}