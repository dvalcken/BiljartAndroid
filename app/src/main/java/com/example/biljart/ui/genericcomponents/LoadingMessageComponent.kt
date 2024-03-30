package com.example.biljart.ui.genericcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.R
import com.example.biljart.ui.theme.BilliardTheme

@Composable
fun LoadingMessageComponent(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
        Text(
            text = message,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_small))
                .wrapContentWidth(Alignment.CenterHorizontally),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingMessageComponentPreview() {
    BilliardTheme(darkTheme = false) {
        LoadingMessageComponent(message = "Loading preview...")
    }
}
