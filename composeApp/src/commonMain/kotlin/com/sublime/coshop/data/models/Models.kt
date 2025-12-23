package com.sublime.coshop.data.models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    @SerialName("display_name")
    val displayName: String,
    val status: String,
    @SerialName("family_id")
    val familyId: String? = null
)

@Serializable
data class ShoppingItem(
    val id: Int? = null,
    val name: String,
    @SerialName("is_bought")
    val isBought: Boolean = false,
    @SerialName("family_id")
    val familyId: String
)
