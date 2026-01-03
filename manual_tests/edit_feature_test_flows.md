# Edit Feature Test Flows

This document outlines all test scenarios for the item edit feature in CoShop.

## Test Flow 1: Basic Edit (No Conflicts)

**Setup:**
- User has item "Milk" (1 gallon, DAIRY, assigned to John)
- No other items named "Whole Milk"

**Steps:**
1. User taps edit icon on "Milk" item
2. Edit dialog opens with current values pre-filled
3. User changes name to "Whole Milk"
4. User changes quantity to "2 gallons"
5. User taps "Save"

**Expected Result:**
- Item updates immediately
- Dialog closes
- Item now shows "Whole Milk, 2 gallons"
- No conflict dialogs appear

---

## Test Flow 2: Edit Only Quantity (No Conflict Check)

**Setup:**
- User has item "Milk" (1 gallon, DAIRY, assigned to John)

**Steps:**
1. User taps edit icon on "Milk" item
2. Edit dialog opens
3. User changes ONLY quantity to "3 gallons"
4. Name, category, and assigned user remain unchanged
5. User taps "Save"

**Expected Result:**
- Item updates immediately
- Dialog closes
- No duplicate detection triggered
- No rename conflict dialog appears
- Item shows "Milk, 3 gallons"

**Rationale:** Quantity changes don't cause name conflicts

---

## Test Flow 3: Edit Only Category (No Conflict Check)

**Setup:**
- User has item "Milk" (1 gallon, DAIRY, assigned to John)

**Steps:**
1. User taps edit icon on "Milk" item
2. Edit dialog opens
3. User changes ONLY category from DAIRY to PANTRY
4. Name, quantity, and assigned user remain unchanged
5. User taps "Save"

**Expected Result:**
- Item updates immediately
- Dialog closes
- No rename conflict dialog appears
- Item shows PANTRY category icon

**Rationale:** Category changes don't cause name conflicts

---

## Test Flow 4: Edit Only Assigned User (No Conflict Check)

**Setup:**
- User has item "Milk" (1 gallon, DAIRY, assigned to John)

**Steps:**
1. User taps edit icon on "Milk" item
2. Edit dialog opens
3. User changes ONLY assigned user from John to Jane
4. Name, quantity, and category remain unchanged
5. User taps "Save"

**Expected Result:**
- Item updates immediately
- Dialog closes
- No rename conflict dialog appears
- Item shows Jane's color/avatar

**Rationale:** Assignment changes don't cause name conflicts

---

## Test Flow 5: Rename to Existing Item Name

**Setup:**
- List has "Milk" (1 gallon, DAIRY, assigned to John)
- List has "Bread" (2 loaves, BAKERY, assigned to Jane)

**Steps:**
1. User taps edit icon on "Milk" item
2. Edit dialog opens
3. User changes name from "Milk" to "Bread"
4. User changes quantity to "5 loaves"
5. User taps "Save"

**Expected Result:**
- Item updates immediately
- Dialog closes
- Item renamed to "Bread, 5 loaves"
- List now has two "Bread" items with different attributes

**Rationale:** Users can rename items to match existing names. Autocomplete (future feature) will help prevent accidental near-duplicates.

---

## Test Flow 6: Input Normalization

**Setup:**
- User has item "Milk" (1 gallon, DAIRY, assigned to John)

**Steps:**
1. User taps edit icon on "Milk" item
2. Edit dialog opens
3. User types "  Whole Milk  " (with leading/trailing spaces)
4. User types "  2 gallons  " for quantity
5. User taps "Save"

**Expected Result:**
- Spaces trimmed automatically
- Item updated to "Whole Milk" (no spaces)
- Quantity shows "2 gallons" (no spaces)
- Dialog closes

---

## Test Flow 7: Edit Dialog Cancel

**Setup:**
- User has item "Milk"

**Steps:**
1. User taps edit icon on "Milk" item
2. Edit dialog opens
3. User makes some changes
4. User taps "Cancel" or outside dialog

**Expected Result:**
- Dialog closes
- Item remains unchanged
- No updates applied

---

## Test Flow 8: Invalid Item ID

**Setup:**
- User somehow triggers edit with non-existent item ID

**Steps:**
1. Edit dialog attempts to open with invalid ID
2. System checks if item exists

**Expected Result:**
- Dialog closes immediately
- No error shown to user
- No items modified

---

## Test Flow 9: Empty/Invalid Input Validation

**Setup:**
- User has item "Milk" (1 gallon, DAIRY, assigned to John)

**Steps:**
1. User taps edit icon on "Milk" item
2. Edit dialog opens
3. User clears name field (empty string)
4. User taps "Save"

**Expected Result:**
- Validation fails
- Item NOT updated
- Dialog remains open (or shows error)

**Note:** Exact validation behavior depends on ValidationUtils implementation

---

## UI State Test Scenarios

### Test: showEditItemDialog()
- `showEditItemDialog` = true
- `editingItem` = selected item

### Test: hideEditItemDialog()
- `showEditItemDialog` = false
- `editingItem` = null

---

## Edge Cases

### Whitespace-Only Name
**Scenario:** User enters "   " (spaces only)
**Result:** Validation fails, no update

### Very Long Names
**Scenario:** User enters 200 character item name
**Result:** Depends on ValidationUtils max length rules

### Special Characters
**Scenario:** User enters "Milk & Cookies 🥛"
**Result:** Should be allowed if ValidationUtils permits

### Network/Sync Issues (Phase 3)
**Scenario:** Edit conflicts with real-time update from another user
**Result:** TBD - Phase 3 conflict resolution

---

## Performance Tests

### Large List Performance
- Edit item in list with 500+ items
- Update should complete quickly (<100ms)
- UI should remain responsive

### Rapid Edits
- User quickly edits multiple items
- Each edit should complete before next starts
- No race conditions

---

## Accessibility Tests

### Screen Reader
- Edit button announces "Edit [item name]"
- Dialog announces fields correctly

### Keyboard Navigation
- Tab through all edit dialog fields
- Enter/Escape keys work as expected
- Focus returns to item after dialog closes

---

## Summary: Key Test Categories

1. **Basic Edits** (Flows 1-5): Field changes including renaming to existing item names
2. **Input Validation** (Flows 6, 9): Normalization and validation
3. **Edge Cases** (Flow 8): Error handling
4. **UI State**: Dialog state management
5. **Performance**: Large lists, rapid actions
6. **Accessibility**: Screen readers, keyboard

**Note:** Rename conflict detection was removed in favor of autocomplete suggestions (future feature). 
Users can now rename items to any name, including existing names. 
This simplifies the UX and relies on proactive prevention rather than reactive blocking.

---
