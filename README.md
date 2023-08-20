# RebtelAssignment
## Country Details App
This is an Android app that allows users to select a country and view its details using modern Android development technologies.

## Features
- List of countries with the ability to select one.
- Display detailed information about the selected country.
- Uses MVVM architecture for clean separation of concerns.
- Utilizes Jetpack Compose for building UI components.
- Integrates Data Binding for efficient UI updates.
- Fetches country data using Retrofit and displays it.
- Loads flag images using Coil image loading library.
- Includes unit tests for critical app components.

## Prerequisites
Android Studio (latest version)
Kotlin programming language

## Getting Started
1. Clone the repository to your local machine:

```
git clone https://github.com/parisa-mohamadi/RebtelAssignment.git
```

1. Open the project in Android Studio.

1. Build and run the app on an emulator or a physical device.

## Architecture
The app follows the MVVM (Model-View-ViewModel) architecture, which promotes the separation of concerns and maintainability.

**Model:** Represents the data structures and business logic of the app.

**View:** Contains the UI components built using Jetpack Compose and Data Binding.

**ViewModel:** Holds the UI-related data and communicates with the Model.

## Dependencies
- Retrofit: For making API requests and handling responses.
- Jetpack Compose: For building modern and responsive UI.
- Data Binding: For binding UI components to data sources.
- LiveData: For observing data changes and updating the UI.
- ViewModel: For managing UI-related data in a lifecycle-conscious way.
- Coil: For efficient and flexible image loading.
- MockK: used in unit testing to create and handle mock objects.
- Coroutines: for asynchronous programming.

## Usage
1. Launch the app on your emulator or device.
1. You will see a drop down menu of countries.
1. Click on or search for a country to view its details.
1. Explore the detailed information about the selected country.
 
## Unit Tests
The app includes unit tests to ensure the correctness of critical app components. To run unit tests:

1. Open the project in Android Studio.
1. Navigate to the test directory under src.
1. Right-click on the test class you want to run.
1. Select "Run" to execute the tests.

## Contributing
Contributions are welcome! If you find any issues or want to add new features, feel free to reach out to me at 
mohamadiparisa312@gmail.com.

