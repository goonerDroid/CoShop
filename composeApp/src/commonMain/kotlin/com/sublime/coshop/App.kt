package com.sublime.coshop

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.sublime.coshop.ui.ShoppingListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold {
            ShoppingListScreen()
        }
    }
}