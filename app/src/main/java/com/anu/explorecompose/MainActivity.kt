package com.anu.explorecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anu.explorecompose.ui.theme.ExploreComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
private fun MyApp(names: List<String> = listOf("World", "Compose")) {
    ExploreComposeTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                for (name in names)
                    Greeting(name)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth(1f)
    ) {
        Row(Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.greeting))
                Text(text = name)
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = stringResource(R.string.show_more_button))
            }
        }
    }
}

@Preview(widthDp = 320)
@Composable
fun DefaultPreview() {
    ExploreComposeTheme() {
        MyApp()
    }
}