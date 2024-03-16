package com.example.biljart

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness4
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
import com.example.biljart.ui.theme.BilliardTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopAppBar(
    toggleTheme: () -> Unit,
    @StringRes title: Int, // lesson 3 2:33:00
    navigationIcon: @Composable () -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(stringResource(title)) },
        navigationIcon = navigationIcon,
        actions = {
            // show three dots icon to open the menu
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Menu",
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
                    text = { Text("Toggle theme") },
                    onClick = {
                        toggleTheme()
                        expanded.value = false // close the menu after clicking
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Brightness4,
                            contentDescription = "Toggle theme",
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
                MyTopAppBar(title = R.string.app_name, toggleTheme = {}) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Home")
                    }
                }
            }
        }
    }
}
