package com.sublime.coshop.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sublime.coshop.data.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel {
    var connectionStatus by mutableStateOf("Testing connection...")
        private set

    fun checkConnection() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val session = SupabaseClient.client.auth.currentSessionOrNull()

                connectionStatus = if (session != null) {
                    "Connected: Logged in as ${session.user?.email}"
                } else {
                    "Connected to Supabase! (No active session)"
                }
            } catch (e: Exception) {
                connectionStatus = "Connection Failed: ${e.message}"
            }
        }
    }
}