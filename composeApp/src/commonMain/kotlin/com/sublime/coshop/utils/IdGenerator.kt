package com.sublime.coshop.utils

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object IdGenerator {
    @OptIn(ExperimentalUuidApi::class)
    fun generateItemId(): String = "item_${Uuid.random()}"

    @OptIn(ExperimentalUuidApi::class)
    fun generateListId(): String = "list_${Uuid.random()}"
}
