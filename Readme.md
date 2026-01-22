# 📝 Clean Architecture Note App

A modern Android note-taking application built with **Clean Architecture**, **Jetpack Compose**, **Room Database**, and **Dagger Hilt** for dependency injection.

## 🏗️ Architecture Overview

This project follows **Clean Architecture** principles, separating the codebase into three distinct layers:

```
┌─────────────────────────────────────────────────────────┐
│                  Presentation Layer                      │
│            (UI, ViewModels, Compose Screens)            │
└────────────────────┬────────────────────────────────────┘
                     │ depends on
┌────────────────────▼────────────────────────────────────┐
│                    Domain Layer                          │
│        (Business Logic, Use Cases, Entities)            │
└────────────────────┬────────────────────────────────────┘
                     │ depends on
┌────────────────────▼────────────────────────────────────┐
│                     Data Layer                           │
│         (Repository Implementation, Room DAO)           │
└─────────────────────────────────────────────────────────┘
```

### Key Principles
- ✅ **Dependency Rule**: Dependencies point inward (outer layers depend on inner layers)
- ✅ **Separation of Concerns**: Each layer has a single, well-defined responsibility
- ✅ **Testability**: Business logic is independent of frameworks and UI
- ✅ **Maintainability**: Easy to modify and extend without breaking existing code

---

## 🚀 Features

- ✨ Create, read, update, and delete notes
- 🎨 Color-coded notes with 5 predefined colors
- 🔄 Sort notes by title, date, or color (ascending/descending)
- ↩️ Undo delete functionality with Snackbar
- 🌙 Dark theme UI with Material Design 3
- 💾 Local persistence with Room Database
- 🔄 Reactive data flow with Kotlin Flow

---

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern declarative UI toolkit
- **Coroutines** - Asynchronous programming
- **Flow** - Reactive data streams

### Architecture Components
- **Room Database** - Local data persistence
- **ViewModel** - UI state management
- **Dagger Hilt** - Dependency injection
- **Navigation Compose** - In-app navigation

### Libraries & Versions
```gradle
// Compose
androidx.compose:compose-bom:2025.01.00
androidx.activity:activity-compose:1.9.3
androidx.navigation:navigation-compose:2.8.3

// Room Database
androidx.room:room-runtime:2.6.1
androidx.room:room-ktx:2.6.1

// Dependency Injection
com.google.dagger:hilt-android:2.51.1
androidx.hilt:hilt-navigation-compose:1.2.0

// Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0
```

---

## 📁 Project Structure

```
app/src/main/java/com/plcoding/cleanarchitecturenoteapp/
│
├── di/                                    # Dependency Injection
│   └── AppModule.kt                       # Hilt module providing dependencies
│
├── feature_note/
│   ├── domain/                            # Business Logic Layer
│   │   ├── model/
│   │   │   └── Note.kt                    # Core entity
│   │   ├── repository/
│   │   │   └── NotesRepository.kt         # Repository interface
│   │   ├── use_case/
│   │   │   ├── AddNoteUseCase.kt          # Validation & save logic
│   │   │   ├── DeleteNoteUseCase.kt       # Delete logic
│   │   │   ├── GetNote.kt                 # Get single note
│   │   │   ├── GetNotesUseCase.kt         # Get & sort all notes
│   │   │   └── NoteUseCase.kt             # Use case wrapper
│   │   └── util/
│   │       ├── NoteOrder.kt               # Sorting options
│   │       └── OrderType.kt               # Asc/Desc order
│   │
│   ├── data/                              # Data Layer
│   │   ├── data_source/
│   │   │   ├── NoteDao.kt                 # Room DAO
│   │   │   └── NoteDatabase.kt            # Room database
│   │   └── repository/
│   │       └── NoteRepositoryImpl.kt      # Repository implementation
│   │
│   └── presentation/                      # Presentation Layer
│       ├── notes/                         # Notes List Screen
│       │   ├── NotesScreen.kt             # Main UI composable
│       │   ├── NotesViewModel.kt          # State management
│       │   ├── NotesState.kt              # UI state
│       │   ├── NotesEvent.kt              # User events
│       │   └── components/
│       │       ├── NoteItem.kt            # Individual note card
│       │       ├── OrderSection.kt        # Sort options UI
│       │       └── DefaultRadioButton.kt  # Radio button component
│       │
│       ├── add_edit_note/                 # Add/Edit Screen
│       │   ├── AddEditNoteViewModel.kt    # State management
│       │   ├── AddEditNoteEvent.kt        # User events
│       │   └── NoteTextFieldState.kt      # Text field state
│       │
│       └── MainActivity.kt                # Entry point
│
├── ui/theme/                              # App theming
│   ├── Color.kt                           # Color palette
│   ├── Theme.kt                           # Material theme
│   └── Type.kt                            # Typography
│
└── NoteApp.kt                             # Application class
```

---

## 🔧 Setup Instructions

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or newer
- JDK 17 or higher
- Minimum SDK: API 21 (Android 5.0)
- Target SDK: API 34 (Android 14)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/CleanArchitectureNoteApp.git
   cd CleanArchitectureNoteApp
   ```

2. **Open in Android Studio**
   - File → Open → Select project directory
   - Wait for Gradle sync to complete

3. **Run the app**
   - Connect an Android device or start an emulator
   - Click the ▶️ Run button or press `Shift + F10`

---

## 🧩 Layer Breakdown

### 1️⃣ **Domain Layer** (Business Logic)

**Purpose**: Contains business rules, entities, and use case definitions.

#### `Note.kt` - Core Entity
```kotlin
@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
)
```

#### `NotesRepository.kt` - Abstract Interface
```kotlin
interface NotesRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
}
```

#### Use Cases
- **AddNoteUseCase**: Validates title/content before saving
- **DeleteNoteUseCase**: Handles note deletion
- **GetNotesUseCase**: Retrieves and sorts notes
- **GetNote**: Fetches a single note by ID

---

### 2️⃣ **Data Layer** (Implementation Details)

**Purpose**: Implements repository interfaces and handles data sources.

#### `NoteDao.kt` - Room Database Interface
```kotlin
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
}
```

#### `NoteRepositoryImpl.kt`
Implements `NotesRepository` interface using Room DAO.

---

### 3️⃣ **Presentation Layer** (UI & State Management)

**Purpose**: Handles UI rendering and user interactions.

#### State Management Pattern
```kotlin
// ViewModel exposes read-only state
private val _state = mutableStateOf(NotesState())
val state: State<NotesState> = _state

// UI sends events
fun onEvent(event: NotesEvent) { ... }
```

#### Key Components
- **NotesScreen**: Main Compose UI displaying note list
- **NotesViewModel**: Manages state and business logic calls
- **NotesState**: UI state (note list, sort order, visibility flags)
- **NotesEvent**: User actions (delete, sort, toggle)

---

## 🎨 UI Components

### NoteItem
Displays individual notes with:
- Custom-shaped card with cut corner design
- Color-coded background
- Title and content preview
- Delete button

### OrderSection
Radio buttons for sorting:
- **By**: Title, Date, Color
- **Order**: Ascending, Descending

---

## 🔄 Data Flow Example

**User deletes a note:**

```
┌─────────────┐
│ NotesScreen │ User clicks delete button
└──────┬──────┘
       │ viewModel.onEvent(DeleteNote(note))
       ▼
┌─────────────────┐
│ NotesViewModel  │ Handles event
└──────┬──────────┘
       │ noteUseCase.deleteNote(note)
       ▼
┌───────────────────┐
│ DeleteNoteUseCase │ Business logic
└──────┬────────────┘
       │ repository.deleteNote(note)
       ▼
┌────────────────────┐
│ NoteRepositoryImpl │ Data layer
└──────┬─────────────┘
       │ dao.deleteNote(note)
       ▼
┌──────────────┐
│ Room Database│ Persists change
└──────┬───────┘
       │ Emits Flow<List<Note>>
       ▼
┌─────────────────┐
│ NotesViewModel  │ Updates _state
└──────┬──────────┘
       │ State change triggers recomposition
       ▼
┌─────────────┐
│ NotesScreen │ UI updates automatically
└─────────────┘
```

---

## 🧪 Testing Strategy

### Unit Tests
- **Use Cases**: Test business logic validation
- **ViewModels**: Test state changes and event handling

### Integration Tests
- **Repository**: Test database operations
- **End-to-End**: Test complete user flows

### Example Test
```kotlin
@Test
fun `Add note with blank title throws exception`() = runTest {
    val useCase = AddNoteUseCase(fakeRepository)
    
    assertThrows<InvalidNoteException> {
        useCase(Note(title = "", content = "Content", ...))
    }
}
```

---

## 🐛 Known Issues & Solutions

### Issue 1: Delete Button Not Working
**Problem**: `onClick = { onDeleteClick }` doesn't invoke the function

**Solution**:
```kotlin
// ❌ Wrong
onClick = { onDeleteClick }

// ✅ Correct
onClick = onDeleteClick
// or
onClick = { onDeleteClick() }
```

### Issue 2: Missing @Module Annotation
**Problem**: Hilt can't find AppModule

**Solution**: Add `@Module` annotation
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule { ... }
```

---

## 📚 Learning Resources

### Clean Architecture
- [Uncle Bob's Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Android Clean Architecture Guide](https://developer.android.com/topic/architecture)

### Jetpack Compose
- [Official Compose Documentation](https://developer.android.com/jetpack/compose)
- [Compose Pathway](https://developer.android.com/courses/pathways/compose)

### Room Database
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable/function names
- Add comments for complex logic

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Author

**Philipp Lackner (PLCoding)**
- YouTube: [@PhilippLackner](https://www.youtube.com/@PhilippLackner)
- GitHub: [@philipplackner](https://github.com/philipplackner)

---

## 🙏 Acknowledgments

- Inspired by Clean Architecture principles by Robert C. Martin
- Built following Android development best practices
- Uses Material Design 3 guidelines

---

## 📱 Screenshots

> Add screenshots of your app here showing:
> - Notes list screen
> - Add/edit note screen
> - Sort options
> - Delete with undo functionality

---

## 🔮 Future Enhancements

- [ ] Add search functionality
- [ ] Implement categories/tags
- [ ] Add rich text formatting
- [ ] Cloud sync with Firebase
- [ ] Widget support
- [ ] Export notes to PDF
- [ ] Dark/Light theme toggle
- [ ] Pin important notes
- [ ] Reminder/notification support

---

## ⚡ Performance Optimizations

- Uses `Flow` for reactive data streaming
- Lazy loading with `LazyColumn`
- Efficient database queries with Room
- Coroutines for async operations
- State hoisting to prevent unnecessary recompositions

---

**Made with ❤️ using Clean Architecture and Jetpack Compose**
