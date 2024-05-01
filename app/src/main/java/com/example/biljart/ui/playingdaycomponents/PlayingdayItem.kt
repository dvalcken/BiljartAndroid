package com.example.biljart.ui.playingdaycomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.R
import com.example.biljart.util.DateUtils

@Composable
fun PlayingdayItem(
    playingdayId: Int,
    date: String,
    isFinished: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val formattedDate = DateUtils.formatDateFromIso(date)
    val status = stringResource(if (isFinished) R.string.finished else R.string.not_finished)
    val icon = if (isFinished) Icons.Filled.CheckCircle else Icons.Filled.HourglassEmpty
    val iconColor = if (isFinished) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    Card(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .clickable(onClick = onClick, interactionSource = remember { MutableInteractionSource() }, indication = null),
        shape = RoundedCornerShape(dimensionResource(R.dimen.cornerradius_small)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = dimensionResource(R.dimen.elevation_small)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = stringResource(R.string.playingday_id, playingdayId),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(R.string.date, formattedDate),
                    style = MaterialTheme.typography.bodyLarge,
                )
//                Text(
//                    text = stringResource(R.string.is_finished, isFinished),
//                    style = MaterialTheme.typography.bodySmall,
//                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = status,
                    tint = iconColor,
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_medium)),
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.padding_small)))
                Text(
                    text = status,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Detail",
                modifier = Modifier.size(dimensionResource(R.dimen.icon_medium)),
            )
        }
    }
}

@Preview
@Composable
fun PreviewPlayingdayItem() {
    MaterialTheme {
        PlayingdayItem(
            playingdayId = 1,
            date = "2024-03-01",
            isFinished = false,
            onClick = {},
        )
    }
}
