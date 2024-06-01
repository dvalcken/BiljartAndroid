package com.example.biljart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.R
import com.example.biljart.ui.theme.BilliardTheme

@Composable
fun StartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(), // fill the available space.
        verticalArrangement = Arrangement.Center, // center the content vertically.
        horizontalAlignment = Alignment.CenterHorizontally, // center the content horizontally.
    ) {
        /*Text(
                modifier = Modifier.padding(8.dp),
                text =
                """
                    Billiard app content comes here.

                    This is just a placeholder for the main content of the app.
                """.trimIndent(),
//                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
            )*/

        val logo = painterResource(R.drawable.logo8ball)
        Surface {
            // show the 8-ball logo
            Image(
                painter = logo,
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
                    .fillMaxWidth() // use max available width
                    .wrapContentHeight(align = Alignment.CenterVertically), // aligns the image vertically in the center
            )
        }
    }
}

// preview
@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    BilliardTheme(darkTheme = false) {
        StartScreen()
    }
}
