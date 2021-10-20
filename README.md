# Rick and Morty

The purpose of the application is to showcase modern Android development the MVVM pattern, Hilt, Retrofit, LiveData, ViewModel, Coroutines, Room, Navigation Components, Data Binding and some other libraries from the [Modern Android Development](https://developer.android.com/modern-android-development) blueprint.

## Features

The app fetches data from the [Rick and Morty API](https://rickandmortyapi.com/documentation).

* The characters are displayed in a Primary/Detail flow.
    * The main screen shows the list of characters.
      * Swipe to refresh gesture,
      * Lazy loading with paging
    * Tapping on the characters the app shown the details of the characters alongside the list of the episodes in the character appeared.
    * The details of the episodes are also can be checked.
    * The characters are able to be marked as favourites. The list of the favourite characters can be opened from the main screen's Toolbar menu
    * On tablets the list and the detail layouts are shown together in a Primary/detail pattern
* The app is distributed via Firebase App Distribution. You can join to the testers via [this invite link](https://appdistribution.firebase.dev/i/c9454bb5a3129d00)

## Technologies used:

The main purpose of the application is to showcase the recent architecture best practices

* [Retrofit](https://square.github.io/retrofit/) a REST Client for Android.
* [Hilt](https://dagger.dev/hilt/) for dependency injection.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) to store and manage UI-related data in a lifecycle conscious way.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) to handle data in a lifecycle-aware fashion.
* [Navigation Component](https://developer.android.com/guide/navigation) a single-activity architecture to handle all navigation and also passing of data between destinations with [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data) plugin.
* [Glide](https://bumptech.github.io/glide/) for image loading.
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) used to manage the local storage i.e. `writing to and reading from the database`. Coroutines help in managing background threads and reduces the need for callbacks.
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) to bind UI components in layouts to data sources.
* [Room](https://developer.android.com/topic/libraries/architecture/room) persistence library which provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Android KTX](https://developer.android.com/kotlin/ktx) provides concise, idiomatic Kotlin to Jetpack, Android platform, and other APIs.
* [Firebase Analytics](https://firebase.google.com/products/analytics) Analytics of the screens usage across the users.
* [Firebase Crashlytics](https://firebase.google.com/products/crashlytics) Crash reports.

## LICENSE
```
MIT License

Copyright (c) 2021 János Kernács

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
