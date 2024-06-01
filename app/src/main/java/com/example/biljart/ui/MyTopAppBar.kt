package com.example.biljart.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.biljart.R
import com.example.biljart.ui.theme.BilliardTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopAppBar(
    toggleTheme: () -> Unit,
    onAbout: () -> Unit,
    @StringRes title: Int, // lesson 3 2:33:00
    navigationIcon: @Composable () -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                stringResource(title),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        navigationIcon = navigationIcon,
        actions = {
            // show three dots icon to open the menu
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.menu),
                )
            }

            // drop down menu
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.padding(top = 4.dp, end = 4.dp, bottom = 4.dp),
            ) {
                // menu item to toggle theme
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(R.string.toggle_theme),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    onClick = {
                        toggleTheme()
                        expanded.value = false // close the menu after clicking
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Brightness4,
                            contentDescription = stringResource(R.string.toggle_theme),
                        )
                    },
                )
                // About menu item
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(R.string.about_title),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    onClick = {
                        // navigate to the about screen
                        onAbout()
                        expanded.value = false // close the menu after clicking
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(R.string.toggle_theme),
                        )
                    },
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    )
}

// preview of the top app bar
@Preview(showBackground = true)
@Composable
fun MyTopAppBarPreview() {
    BilliardTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box {
                MyTopAppBar(title = R.string.app_name, toggleTheme = {}, onAbout = {}) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Home")
                    }
                }
            }
        }
    }
}
