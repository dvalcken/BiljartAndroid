package com.example.biljart.ui.genericcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.R
import com.example.biljart.ui.theme.BilliardTheme

@Composable
fun ErrorMessageComponent(message: String) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.something_went_wrong),
            contentDescription = "Error Icon",
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
        )
        Text(
            text = message,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small)),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMessageComponentPreview() {
    BilliardTheme(darkTheme = false) {
        ErrorMessageComponent(message = "Error preview...")
    }
}
