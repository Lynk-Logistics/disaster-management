# disaster-management
Lynk Hackathon 2019 - Disaster Management

- Team Dwarsoft -

Tech Stack -

1. Android Studio - Java, XML
2. Backend - Node JS
3. Database - MySQL

- Postman Docs of the APIs - https://documenter.getpostman.com/view/6059098/SVtWvmjC?version=latest#51227406-b3f9-4064-aec2-ac7cfbbac259
- Please find the attached the APK of the Android App in the root folder. (Please change the IP address accordingly)
- App Screenshots - https://drive.google.com/drive/folders/1MUGRaxoNR_W55YQGdvYNxZhcOwa69tS_?usp=sharing

The following is the description of our project -

Android - 
 - Written in Android Native Java Platform.
 - APK size is well optimized using multiple techniques such as R8, shrinking the resources, lint checks and others 
obfuscation algoriths (Less than 1.8 MB for the release build)
 - Google's Volley is being used for the Networking calls which has the least size of the aar with great thread mechanism.
 - Google's Material Design Theme has been followed accordingly throughout the application.
 - Localization - The app can be used in multiple languages (helps in conditions where locals can use it easily) - currently
supports two languages but can be easily extended to any number of languages.
 - App is also interconnected with Twitter, Telegram and many other social networking platforms for better reach.
 - Local caching has been adopted using Google's Room Database which in-turn is one of the best local storage libraries available for Android.

Backend -
 - MySQL - RDBMS has been used to create multiple tables (currently 16 tables).
 - Proper Foriegn Key and Naming has been followed along with the DBMS normalized approach.
 - Node JS - 25 API Endpoints has been written addressing various use cases of the application making all the features of the app as much dynamic as possible.