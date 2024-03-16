package com.example.biljart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun StartScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
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
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth() // uses  max available width
                    .wrapContentHeight(align = Alignment.CenterVertically), // aligns the image vertically in the center
            )
        }
    }
}
