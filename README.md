#  Deloitte-Flicker-search 
![screenshot](./app/src/main/res/drawable/utuwap.png)  

UTU Weather App

![](https://img.shields.io/badge/Code-Kotlin%2FJava-brightgreen)

> A weather app with intuitive design

![screenshot](./utuwapgif.gif)

Location based weather details + Weather details for { Sydney , Hobart , Perth }

## Built With

- Android
- Kotlin / Java
- Rest-Api

## Features

Location Based weather from user's lattitude and longitude
Swipe left or right to Change Weather details -> city
15 Day Weather Forecast when clicked on item in list ( 7 Day Weather data from 'openweathermap' Api + Mock Data )


## Main Components and Libraries

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

- GitHub: [@Mane](https://github.com/ManendraDeMel)
- LinkedIn: [Manendra Melbourne,Victoria](https://www.linkedin.com/in/manendra-de-mel)
- Website:[Personal Website](https://mnc22.com)

## ⭐️ Acknowledgments

- Retrofit by Square
- jsonschema2pojo

## 📝 License

This project is [MIT](lic.url) licensed.
