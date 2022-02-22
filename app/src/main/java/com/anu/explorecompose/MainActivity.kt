package com.anu.explorecompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anu.explorecompose.ui.theme.BasicsCodelabTheme
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
private fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
   if (shouldShowOnboarding) {
       OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
   } else {
       Greetings()
   }
}

@Composable
fun Greetings(names: List<String> = List(1000) {"$it"} ) {
    BasicsCodelabTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                items(items = names) { name ->
                    Greeting(name)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    val extraPadding by animateDpAsState(targetValue = if (expanded) 56.dp else 0.dp,
    animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow))

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth(1f)
    ) {
        Row(Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.greeting))
                Text(text = name, style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.ExtraBold))
            }
            OutlinedButton(onClick = {
                expanded = !expanded
            }, modifier = Modifier.padding(bottom = extraPadding.coerceAtLeast(0.dp))) {
                Text(
                    text = stringResource(
                        if (expanded) R.string.show_less_button else
                            R.string.show_more_button
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES, name = "DefaultPreviewInDarkMode")
@Preview(widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    // TODO: This state should be hoisted

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(modifier = Modifier.padding(vertical = 24.dp), onClick = onContinueClicked) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ExploreComposeTheme() {
        OnboardingScreen(onContinueClicked = {})
    }
}