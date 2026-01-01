package com.sublime.coshop.data

import com.sublime.coshop.AppConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = AppConfig.SUPABASE_URL,
        supabaseKey = AppConfig.SUPABASE_KEY,
    ) {
        install(Auth.Companion)
        install(Postgrest.Companion)
        install(Realtime.Companion)
    }
}
