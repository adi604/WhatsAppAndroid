# WhatAppAndroid

The project divided into three sub-projects:

1. Android

2. WEB-API.NET (Web Service)

### Android

An android chat app that support login, registration, creation of new contacts and chatting with contacts.

The app has 4 main activities:

* MainActivity - the login activity.

* RegisterActivity - the register activity, used in order to register to the app by filling a form of username, password, nickname and an image.

* ContactsActivity - whenever a user log in the contacts activity being showed. The user can add a new contact and move to the ChatActivity in order to chat with a specific contact

* ChatActivity - the chat activity, being used in order to chat between two users.

Thechnologies:

* FireBase - used for notification whenever a new message arrived

* Room - a local DB used in order to store all of the users contacts and messages

* Java

The app communicate with the WEB-API Service in order to get the data of each user.

### WEB-API.NET

This is the web service, based on WEB-API .NET and localDB. Http queries can be sent and be responded by the service.

Thechnologies:

* JWT - Whenver a user signing in, the Service sends him a JWT token that identifies the specific user.

* SignalR - being used in order to notify every user that a new message had accured or if another user added this specific user to it's contacts.

* Enity Framework - used to communicate with the database.

This project will run on localhost:5028.

### Run Project

Before you run the project please install @microsoft/signalr Then do the following:

1. Clone the project to your local computer.

2. Open the MVC solution in Microsoft Visual Studio, then set WebAPI.NET as startup project and run it

3. Open the anroid folder with Andriod Studio and run, select an emulator and run.

### Submit

The project owners are:

Adi Aviv 206962904

Guy Ben Razon 209207364

