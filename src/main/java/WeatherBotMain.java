//====================================================================================================================================================================
// Name        : WeatherBotMain.java
// Author      : Jonathan Wachholz (JHW190002)
// Course	   : UTDallas CS 2336.501 Spring
// Version     : 1.0
// Copyright   : March. 2020
// Description :
//
//   Driver file to start / run the weather bot implemented in the rest of this project
//====================================================================================================================================================================
//   This program is dependent on the following external libraries imported through a Maven framework:
//          org.apache.httpcomponents  - for the CloseableHttpClient and other classes used
//          org.json                   - for the json parsing classes used
//          org.pircbotx               - for the PircBotX class used as the base class for the bot
//
//====================================================================================================================================================================


public class WeatherBotMain {

    public static void main(String[] args) {

        MyPircBot myBot = new MyPircBot();

    }
}
