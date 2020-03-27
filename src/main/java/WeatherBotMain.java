//============================================================================
// Name        : WeatherBotMain.java
// Author      : Jonathan Wachholz (JHW190002)
// Course	   : UTDallas CS 2336.501 Spring
// Version     : 1.0
// Copyright   : March. 2020
// Description :
//   Driver file to start / run the weather bot implemented in the rest of this project
//============================================================================


public class WeatherBotMain {

    public static void main(String[] args) {

        /*WeatherData myData = OpenWeatherAPI.callCityAPI("Sydney");
        Vector<String> myVect = myData.dataDescription();
        System.out.println(" ");
        MediaWikiAPI.callSearchAPI("Dallas", 5);
        System.out.println("Test");*/
        MyPircBot myBot = new MyPircBot();
        /*System.out.println("My newest push");
        System.out.println("Newer Push Test");
        HttpClient myClient = HttpClient.newHttpClient();*/
    }
}
