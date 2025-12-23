package com.sublime.coshop

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform