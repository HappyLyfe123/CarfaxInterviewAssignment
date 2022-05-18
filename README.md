# CarfaxInterviewAssignment

The goal of this project was to showcase my Android skill in building a simple Androind App using Kotlin. This app reqire to show a list of car 

## Table of Contents
1. [Tech Stack](#tech-stack)
2. [Images](#images)

## architecture
* MVVM + Clean: Model-View-ViewModel. The app uses MVVM becuase it allow us to spread the logic. This sepration allow us to unit test much easier.
* Room: A presistence library that provides an abstraction layer over SQLLite. Room was used to cache the vehicle details that was return from the remote Api call. 
* Glide: It an image loading library. The app uses glide to load the image using the URL that was provided. Then the image get cache to the memory and cache incase the user don't have internet connection anymore.
* Hilt: It an dependency injection framework. 
* Retrofix and Okhttp: This is use to for making REST request to the server to get the car information.
* Junit: This is a unit testing framewrok.
* Mockk: This is use for mocking the data that's need for testing.
## Images