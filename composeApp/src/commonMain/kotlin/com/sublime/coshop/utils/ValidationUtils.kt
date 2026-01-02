package com.sublime.coshop.utils

data class ValidationResult(val isValid: Boolean, val errorMessage: String? = null, val warningMessage: String? = null)

object ValidationUtils {
    private const val WARN_ITEM_NAME_LENGTH = 200
    private const val WARN_QUANTITY_LENGTH = 50

    fun validateItemName(name: String): ValidationResult {
        val trimmed = name.trim()
        return when {
            trimmed.isBlank() -> ValidationResult(false, errorMessage = "Item name is required")
            trimmed.length > WARN_ITEM_NAME_LENGTH -> {
                ValidationResult(
                    true,
                    warningMessage = "This name is quite long. Consider shortening it for better readability.",
                )
            }
            else -> ValidationResult(true)
        }
    }

    fun validateQuantity(quantity: String): ValidationResult {
        val trimmed = quantity.trim()
        return when {
            trimmed.isBlank() -> ValidationResult(false, errorMessage = "Quantity is required")
            trimmed.length > WARN_QUANTITY_LENGTH -> {
                ValidationResult(
                    true,
                    warningMessage = "This quantity description is quite long.",
                )
            }
            else -> ValidationResult(true)
        }
    }

    fun validateListName(name: String): ValidationResult {
        val trimmed = name.trim()
        return when {
            trimmed.isBlank() -> ValidationResult(false, errorMessage = "List name is required")
            else -> ValidationResult(true)
        }
    }

    fun normalizeItemName(name: String): String = name.trim()

    fun normalizeQuantity(quantity: String): String = quantity.trim()

    fun normalizeListName(name: String): String = name.trim()
}
