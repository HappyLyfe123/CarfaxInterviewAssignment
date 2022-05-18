# CarfaxInterviewAssignment

The goal of this project was to showcase my Android skill in building a simple Android App using Kotlin. This app require to show a list of
cars. Each item in the list consists of an image and some information about the car. The use could click on on the car image which will show
more detail about the car, or click on the call dealer button which will call the dealer. Finally, the app must have ability to work when
there no internet connection.

## Table of Contents

1. [Architecture](#Architecture)
2. [Images](#images)

## Architecture

* MVVM + Clean: Model-View-ViewModel. The app uses MVVM because it allow us to spread the logic. This separation allow us to unit test much
  easier. Clean is use to make it more transparent to the developer to be able to identify what a program performs merely by looking at its
  source code.
* Room: A persistence library that provides an abstraction layer over SQLLite. Room was used to cache the vehicle details that was return
  from the remote Api call.
* Glide: It an image loading library. The app uses glide to load the image using the URL that was provided. Then the image get cache to the
  memory and cache in-case the user don't have internet connection anymore.
* Hilt: It an dependency injection framework.
* Retrofit and Okhttp: This is use to for making REST request to the server to get the car information.
* Junit: This is a unit testing framework.
* Mockk: This is use for mocking the data that's need for testing.

## Images