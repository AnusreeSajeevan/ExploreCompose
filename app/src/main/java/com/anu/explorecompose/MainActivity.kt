package com.anu.explorecompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anu.explorecompose.domain.Message
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
    ExploreComposeTheme {
        var shouldShowChatWindow by rememberSaveable { mutableStateOf(false) }
        var shouldShowGreetings by rememberSaveable { mutableStateOf(false) }
        when {
            shouldShowGreetings -> {
                BasicsCodelabTheme {
                    Greetings()
                }
            }
            shouldShowChatWindow -> {
                createChatView()
            }
            else -> {
                OnboardingScreen(onContinueClicked = { shouldShowGreetings = true }) {
                    shouldShowChatWindow = true
                }
            }
        }
    }
}

@Composable
fun Greetings(names: List<String> = List(1000) { "$it" }) {
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

@Composable
fun Greeting(name: String) {

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth(1f)
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(Modifier.padding(24.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.greeting))
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.ExtraBold)
                    )
                }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) {
                            stringResource(R.string.show_less_button)
                        } else {
                            stringResource(R.string.show_more_button)
                        }

                    )
                }
            }
            if (expanded)
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dolor nisl",
                    modifier = Modifier.padding(16.dp)
                )
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit, onChatWindowClicked: () -> Unit) {
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
            Button(modifier = Modifier.padding(vertical = 24.dp), onClick = onChatWindowClicked) {
                Text("Continue")
            }
        }
    }
}

@Composable
fun createChatView() {
        MessageCard(Message("Anusree", "Android Dev"))
}

@Composable
fun MessageCard(message: Message) {
    Surface(color = MaterialTheme.colors.onSurface) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img), contentDescription = "user",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = message.author,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.secondaryVariant
                )

                Surface(shape = MaterialTheme.shapes.medium) {
                    Text(
                        text = message.message,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageWindowPreview() {
    createChatView()
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MessageWindowPreviewDarkMode() {
    createChatView()
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ExploreComposeTheme {
        OnboardingScreen(onContinueClicked = {}, onChatWindowClicked = {})
    }
}
