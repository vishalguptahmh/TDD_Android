package com.vishalgupta.learntdd

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vishalgupta.learntdd.playList.PlayList
import com.vishalgupta.learntdd.ui.Loader
import com.vishalgupta.learntdd.ui.theme.LearnTDDTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnTDDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding),viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainActivityViewModel) {
    val navController = rememberNavController()
    NavHost(navController,
        startDestination = "list"
    ) {
        composable("list") {
            val playlistResult = viewModel.playList.collectAsState(initial = null)
            val playList = playlistResult.value?.getOrNull() ?: emptyList()

            Greeting(modifier,
            onItemClick = { id ->
                navController.navigate("details/$id")
            },
            playList = playList,
            isLoading = viewModel.isLoading.collectAsState(false).value
            )
        }
        composable("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            LaunchedEffect(id) {
                viewModel.getPlayListDetails(id?:"1")
            }
            DetailsScreen(
                modifier,
                viewModel.isLoading.collectAsState(false).value,
                viewModel,
                id)
        }
    }
}

@Composable
fun DetailsScreen(modifier: Modifier,isLoading: Boolean,viewModel: MainActivityViewModel,id:String?) {
    val detailResultState = viewModel.playListDetail.collectAsState(initial = null)
    val detailResult = detailResultState.value
    val context = LocalContext.current

    if(detailResult?.isFailure == true){
        LaunchedEffect(detailResult) {
            Toast.makeText(context, "${detailResult.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
        }
    }

    val detail  = detailResult?.getOrNull()

    if(isLoading){
        Loader(modifier)
    }else{
        if(detail!=null){
            Column(modifier) {
                Text(
                    text = detail.name,
                    modifier = modifier.padding(16.dp)
                )
                Text(
                    text = detail.details,
                    modifier = modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier,
    playList: List<PlayList>,
    isLoading: Boolean,
    onItemClick: (String) -> Unit
) {
    Spacer(modifier = Modifier.padding(16.dp))

    if (isLoading) {
        Loader(modifier)
    }
    LazyColumn(modifier = modifier
    ) {
        items(playList) { item ->
            Text(
                text = item.name, // Assuming PlayList has a 'name' property
                modifier = Modifier.padding(8.dp).clickable{
                    onItemClick(item.id) // Assuming PlayList has a 'name' property
                }
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    LearnTDDTheme {
//        Greeting("Android", viewModel = MainActivityViewModel(PlayListRepository(PlayListService(object : PlayListApi {
//            override suspend fun getPlayList(): List<PlayListRaw> {
//                return emptyList() // Mocked data for preview
//            }
//        }), mapper = PlayListMapper())))
//    }
//}
