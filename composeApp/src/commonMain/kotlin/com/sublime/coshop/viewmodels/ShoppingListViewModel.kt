package com.sublime.coshop.viewmodels

class ShoppingListViewModel {

//    private val _items = MutableStateFlow(
//        listOf(
//            ShoppingItem(id = 1, name = "Milk", familyId = "1"),
//            ShoppingItem(id = 2, name = "Bread", isBought = true, familyId = "1"),
//            ShoppingItem(id = 3, name = "Eggs", familyId = "1")
//        )
//    )
//    val items = _items.asStateFlow()
//
//    fun onBoughtChange(item: ShoppingItem, isBought: Boolean) {
//        _items.update { currentItems ->
//            currentItems.map {
//                if (it.id == item.id) {
//                    it.copy(isBought = isBought)
//                } else {
//                    it
//                }
//            }
//        }
//    }
//
//    fun addItem(name: String) {
//        _items.update { currentItems ->
//            val newItem = ShoppingItem(
//                id = (currentItems.maxOfOrNull { it.id ?: 0 } ?: 0) + 1,
//                name = name,
//                familyId = "1" // Hardcoded for now
//            )
//            currentItems + newItem
//        }
//    }
}
