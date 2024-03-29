//====================================================================================================================================================================
// Name        : TOKENS_EXAMPLE.java
// Author      : Jonathan Wachholz (JHW190002)
// Course	   : UTDallas CS 2336.501 Spring
// Version     : 1.0
// Copyright   : March. 2020
// Description :
//   Static class used to store private information not meant to be uploaded online / to GitHub
//          contains API key values for the various APIs used
//====================================================================================================================================================================
//This file is for GitHub documentation purposes only
//  Anyone downloading this repo needs to:
//      1) Rename this file to TOKENS.java
//      2) Change the Class name to TOKENS
//          3)Go to both https://openweathermap.org/
//          4)And https://developer.mapquest.com/documentation/
//              Sign up for a free account and insert your API Keys into the Quotes below
//
//====================================================================================================================================================================
public class TOKENS_EXAMPLE {


    public static String getOpenWeatherAPIKEY() { return  "INSERT_OpenWeatherAPIKeyHere"; }

    public static String getMapQuestAPIKEY(){ return "INSERT_MapQuestAPIKeyHere";}

    private static String getMediaWikiAPIKEY(){return "NO API KEY NEEDED AT THIS TIME";} // Just here for future use
}
