#  Deloitte-Flicker-search 
![screenshot](./app/src/main/res/drawable/utuwap.png)  

UTU Weather App

![](https://img.shields.io/badge/Code-Kotlin%2FJava-brightgreen)

> A image searching app with intuitive design

![screenshot](./utuwapgif.gif)

mobile app that uses the Flickr image search API and shows the results in a 3-column scrollable view.

## Built With

- Android
- Kotlin 
- Flickr Rest-Api

## Added Dependancies

```kotlin
 //NETWORK DEPENDANCIES
    implementation "com.squareup.retrofit2:retrofit:$version_retrofit"
    implementation "com.google.code.gson:gson:$version_gson"
    implementation "com.squareup.retrofit2:converter-gson:$version_gson_converter"
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.9.0'

    //IMAGE VIEW RENDERING DEPENDANCIES
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    implementation "pl.droidsonroids.gif:android-gif-drawable:1.2.18"

    //RX JAVA DEPENDANCIES
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.0'

    //DI DEPENDANCIES
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
```


## Architecture Pattern and Components overview

**MainActivity.kt** -> **CityFragment.kt** ( implements call back to mainactivity for onSwipe function ) -> next city fragment<br/><br/>
3 similar Fragments -> {SydneyFragment , HobartFragment , PerthFragment}<br/><br/>
**City.kt** (Main Networking class) using retrofit by sqaure - the only 3rd part library used in the whole project<br/><br/>
**WapViewModel.kt** ( Main ViewModelClass context being mainactivity ) - invoked on onCreateView of Fragments<br/><br/>
OncreateView of CityFragment uses factorymethod to create ViewModel passing lat , lon of current user - **LatLon.java** (GeoLocation class coded)<br/><br/>
**WapViewModel.kt** is initialised with Data for the cities calling city.getWeather(Lat,Lon) -> WapViewModel creates listof(DailyWeather objects) utilizing Api Data<br/><br/>
**jk.kt** is a pojo class ( Json to Kotlin object ) -> **JKList.kt** is called on retrofit http response callback and stores 7 day weather data in 'jk' objects<br/><br/>
**Utility.kt** is used for simple time and date convertions<br/><br/>
**SwipeChecker.kt** is a listener class for swipe gestures


## Setup

2 Options
1. Use apk provided - drag and drop to emulator 
2. Clone github repository to Android Studio Project Directory and Build.




## Authors

👤 **Manendra De Mel**

- GitHub:[@Mane](https://github.com/MDeMel-Dev)
- LinkedIn:[Manendra Melbourne,Victoria](https://www.linkedin.com/in/manendra-de-mel)
- Website:[Personal Website](https://mnc22.com)

## ⭐️ Acknowledgments

- Retrofit by Square
- droidsonroids.gif.drawable

## 📝 License

This project is [MIT](lic.url) licensed.
