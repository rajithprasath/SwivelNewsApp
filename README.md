# SwivelNewsApp

This is a news app which written in kotlin

# Features
User can view top headlines 
User can view news by their preferences(eg: Bitcoin,Animal,Apple,Earthquake)
User can view the full news by clicking on the headlines
User can Register and Logout in Profile tab


# The app has following packages:
model: It contains all the data accessing and manipulating components.
di: Dependency providing classes using Dagger2.
ui: View classes which contains user interactions
viewmodel: Viewmodel classes which corresponding to the views
utils: Utility classes.
network: Its contains all the network related classes
adapter: It contains recyclerview adapter classes
database: Its contains Database Entity and Dao operations classes
mapper: Mapper claases which maps data flows

# Used components and frameworks
In this project, I have used Android Architecture Components and AndroidX
1) RxJava,RxAndroid - Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java ViewModel.
2) LiveData - LiveData is a data holder class that can be observed within a given lifecycle. Usually, you use LiveData to communicate a ViewModel with a View. In this project you'll find different patterns showcasing the liveData builder that lets you control a LiveData from a coroutine block.
3) Dagger2 - Dagger is a compile-time framework for dependency injection. It uses no reflection or runtime bytecode generation, does all its analysis at compile-time, and generates plain Java source code.
4) Databinding -  Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
5) Room Database - he Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
6) Paging - he Paging Library helps you load and display small chunks of data at a time. Loading partial data on demand reduces usage of network bandwidth and system resources.
7) Navigation - Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app. 

# Installation and Testing 
if you face a DaggarAppComponent Error, Rebuild the project and import DaggarAppComponent
