package com.example.biljart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun BiljartApp(navController: NavHostController = rememberNavController()) {
// show billiard table background
    Surface(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.biljarttafeverticaal),
            contentDescription = "billiard table",
            contentScale = ContentScale.FillWidth,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BiljartApp()
}
