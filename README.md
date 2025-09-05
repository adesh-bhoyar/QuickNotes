
# DSA + QuickNotes Mini App Project

This repository contains two parts:

1. **Part A – DSA Warm-Up**
   - Problem: **Merge Overlapping Intervals** implemented in Kotlin.
   - Includes unit tests, complexity analysis, and dynamic input handling.

2. **Part B – QuickNotes Mini App**
   - A simple notes application built with **Kotlin**, **Jetpack Compose**, and **MVVM** architecture.
   - Features: list of notes, search, add/update note, view details, and delete functionality.

---

## 📂 **Project Structure**
```
efile/
│
├── dsa/
│   ├── MergeIntervals.kt       # Implementation of mergeIntervals
│   ├── MergeIntervalsTest.kt   # JUnit tests for mergeIntervals
│   └── README.md               # DSA-specific details
│
└── quicknotes/
    ├── app/
    │   ├── ui/                 # Compose UI screens: List, Search, Detail
    │   ├── data/               # NotesRepository, DAO, Room Database
    │   ├── viewmodel/          # ViewModels for Note List and Detail
    │   └── ...
    └── README.md               # QuickNotes-specific details
```

---

## ✅ **How to Run**

### **Part A: DSA**
1. Open `efile/dsa/` in **IntelliJ IDEA** or any Kotlin-supported IDE.
2. Run `main()` in `MergeIntervals.kt`.
3. For unit tests:
   - Ensure **JUnit 5** is added.
   - Run `MergeIntervalsTest.kt`.

**Command-line (optional):**
```bash
kotlinc MergeIntervals.kt -include-runtime -d merge.jar
java -jar merge.jar
```

---

### **Part B: QuickNotes Mini App**
1. Open `quicknotes/` in **Android Studio**.
2. Connect a device/emulator.
3. Click **Run** ▶ to install and launch the app.

---

## ✅ **DSA Approach – Merge Intervals**
### **Problem**
Given `n` intervals, merge all overlapping or touching intervals into non-overlapping intervals.

### **Algorithm**
- **Sort** intervals by start time.
- Initialize `current` interval as the first interval.
- Iterate through the list:
  - If the next interval **overlaps** or **touches**, merge them.
  - Otherwise, add `current` to the result and move to the next.
- Add the last interval to the result.

### **Complexity**
- **Time:** `O(n log n)` (due to sorting)
- **Space:** `O(n)` (for output list)
- **Inclusive/Exclusive Bounds:** This implementation assumes **inclusive bounds** (`IntRange` in Kotlin).
- If `mergeTouching = true`, `(1..3)` and `(4..5)` merge into `(1..5)`.

### **Trade-Offs**
- Sorting is required, so cannot achieve better than `O(n log n)` without constraints.
- Using a **sweep-line algorithm** with event points is an alternative but adds complexity.

### **References**
- [LeetCode – Merge Intervals](https://leetcode.com/problems/merge-intervals/)
- [Kotlin Ranges](https://kotlinlang.org/docs/ranges.html)

---

## ✅ **QuickNotes Mini App – Brief Architecture**
- **Architecture:** MVVM (Model-View-ViewModel)
- **UI Layer:** Jetpack Compose screens
  - **List Screen:** Displays all notes, supports **search** and **delete**.
  - **Detail Screen:** Add or edit a note.
- **Data Layer:** Room Database
  - `Note` entity, `NoteDao` for CRUD operations.
- **Repository:** Provides data from DB to ViewModel.
- **ViewModel:** Handles UI state, coroutine operations for DB.

---

### **Trade-Offs for QuickNotes**
- Simple architecture without DI (to keep lightweight).
- Uses **StateFlow** for reactive UI updates.
- Could be enhanced with **Hilt** for dependency injection.

---

## ✅ **References**
- [Android Developers: Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
