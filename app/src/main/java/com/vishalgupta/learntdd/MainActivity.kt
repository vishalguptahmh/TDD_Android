package com.vishalgupta.learntdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vishalgupta.learntdd.ui.theme.LearnTDDTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
//        val engine = Engine()
//        val car = Car(20.0, engine)
//
//        car.turnOn()


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        viewModel = MainActivityViewModel(repository = PlayListRepository())
        setContent {
            LearnTDDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, viewModel: MainActivityViewModel) {

    val playlistResult = viewModel.playList.collectAsState(initial = null)
    val playList= playlistResult.value?.getOrNull()?:emptyList()

    Text(
        text = "Hello $name!",
        modifier = Modifier.padding(16.dp)
    )
    LazyColumn (
        modifier = modifier
    ) {
        items(playList) { item ->
            Text(
                text = item.name, // Assuming PlayList has a 'name' property
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LearnTDDTheme {
        Greeting("Android", viewModel = MainActivityViewModel(PlayListRepository(PlayListService(object : PlayListApi{
            override suspend fun getPlayList(): List<PlayList> {
                return emptyList() // Mocked data for preview
            }
        }))))
    }
}