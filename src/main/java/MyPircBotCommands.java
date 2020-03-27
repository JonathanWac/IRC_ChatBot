//====================================================================================================================================================================
// Name        : MyPircBotCommands.java
// Author      : Jonathan Wachholz (JHW190002)
// Course	   : UTDallas CS 2336.501 Spring
// Version     : 1.0
// Copyright   : March. 2020
// Description :
//          MyPircBotCommands is a listener class that extends from the PircBotX ListenerAdapter class which implements the base java Listener class.
//              Currently, this class will check every message sent inside the channels the bot is apart of for Keywords to trigger
//              the given bot commands.
//
//          The current commands are:
//              !Help or !Commands - which displays the commands meant to be used by regular users, and thus will not show the ADMIN commands
//              !Weather "search string" - will display the weather information for a given search term
//              !Wiki search "search string" - will display the first 5 sentences from a given Wikipedia search strings summary page
//
//          The following are Admin commands that will not be shown by !Help / !Commands
//              !ADMIN changech "channel name" - will cause the bot to join the new chat channel
//              !ADMIN exit - will cause the bot to disconnect from the IRC entirely, leaving all channels
//
//====================================================================================================================================================================


import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.Vector;

public class MyPircBotCommands extends ListenerAdapter {

    public boolean isInt(String string){
        try{
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException error){
            return false;
        }
    }

    public boolean isUS_State(String string){
        String[] US_States_Array = {
                "AL", "Alabama", "Montana", "MT",
                "Alaska", "AK", "Nebraska", "NE",
                "Arizona", "AZ", "Nevada", "NV",
                "Arkansas", "AR", "NewHampshire", "NH",
                "California", "CA", "NewJersey", "NJ",
                "Colorado", "CO", "NewMexico", "NM",
                "Connecticut", "CT", "NewYork", "NY",
                "Delaware", "DE", "NorthCarolina", "NC",
                "Florida", "FL", "NorthDakota", "ND",
                "Georgia", "GA", "Ohio", "OH",
                "Hawaii", "HI", "Oklahoma", "OK",
                "Idaho", "ID", "Oregon", "OR",
                "Illinois", "IL", "Pennsylvania", "PA",
                "Indiana", "IN", "RhodeIsland", "RI",
                "Iowa", "IA", "SouthCarolina", "SC",
                "Kansas", "KS", "SouthDakota", "SD",
                "Kentucky", "KY", "Tennessee", "TN",
                "Louisiana", "LA", "Texas", "TX",
                "Maine", "ME", "Utah", "UT",
                "Maryland", "MD", "Vermont", "VT",
                "Massachusetts", "MA", "Virginia", "VA",
                "Michigan", "MI", "Washington", "WA",
                "Minnesota", "MN", "WestVirginia", "WV",
                "Mississippi", "MS", "Wisconsin", "WI",
                "Missouri", "MO", "Wyoming", "WY"
        };
        for (String s : US_States_Array) {
            if (s.toLowerCase().equals(string.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void onGenericMessage(GenericMessageEvent event){}
    public void onMessage(MessageEvent event){
        String msg = event.getMessage().trim();

        if (msg.startsWith("!Help") || msg.startsWith("!Commands")){
            event.respond("List of commands:");
            event.respond("\t!Weather \"Search String\" ");
            event.respond("\t!Wiki search \"Search String\"");
            event.respond("\t!Help or !Commands");
        }
        else if (msg.startsWith("!ADMIN")){
            String adminMsg = msg;
            adminMsg = adminMsg.replace(',', ' ');
            String[] args = adminMsg.split(" +");
            if (args[1].toLowerCase().equals("changech") && args.length >= 3){
                String channelName2 = args[2];
                event.respondWith("Now joining the channel "+channelName2);
                event.getBot().send().joinChannel(channelName2);
            }
            else if(args[1].toLowerCase().equals("exit")){
                event.respondWith("Quitting because of command: " +msg);
                event.getBot().send().quitServer();
            }
        }
        else if (msg.startsWith("!Wiki")){
            String wikiMsg = msg;
            wikiMsg = wikiMsg.replace(',', ' ');
            String[] args = wikiMsg.split(" +");
            if (args[1].toLowerCase().equals("search")){
                StringBuilder searchStringBuilder;
                if (args.length >= 4){
                    searchStringBuilder = new StringBuilder(args[2]);
                    for (int i = 3; i < args.length; i++){
                        searchStringBuilder.append("+"+args[i]);
                    }
                    WikiPageInfo[] results = MediaWikiAPI.callSearchAPI(searchStringBuilder.toString(), 1).toArray(new WikiPageInfo[0]);
                    for (int i = 0; i < results.length; i++){
                        event.respondWith(String.format("Result %d: %s\tURL: %s", i+1, results[i].getTitle(), results[i].getUrl()));
                        event.respondWith("\t"+results[i].getSummary());
                    }
                }
                else if (args.length == 3){
                    WikiPageInfo[] results = MediaWikiAPI.callSearchAPI(args[2], 1).toArray(new WikiPageInfo[0]);
                    for (int i = 0; i < results.length; i++){
                        event.respondWith(String.format("Result %d: %s\tURL: %s", i+1, results[i].getTitle(), results[i].getUrl()));
                        event.respondWith("\t"+results[i].getSummary());
                    }
                }
            }
        }
        else if (msg.startsWith("!Weather")){
            String weatherMsg = msg;
            weatherMsg = weatherMsg.replace("!Weather", "");
            if (weatherMsg.startsWith(" ")){
                weatherMsg = weatherMsg.replaceFirst(" +", "");
            }
            Vector<Float> myLatLongCoords = MapQuestAPI.call_LatLongAPI(weatherMsg);
            WeatherData weatherData = OpenWeatherAPI.callLatLongAPI(myLatLongCoords.get(0), myLatLongCoords.get(1));
            weatherData.origSearchString = weatherMsg;

            printWeatherData(event, weatherData);
            //===========================================================================================================
            // This code block is no longer needed as the bot now calls the MapQuest API which will parse our search term
            //      more accurately than doing it ourselves...
            //  Code is left here for future implementation of the OpenWeather API
            //===========================================================================================================
            /*String weatherMsg = msg;
            weatherMsg = weatherMsg.replace(',', ' ');
            String[] args = weatherMsg.split(" +");

            int zipCodeIndex = -99;

            for (int i = 0; i < args.length; i++){
                if (isInt(args[i])){
                    zipCodeIndex = i;
                }
            }
            if (args.length == 2){
                if (zipCodeIndex == 1){             //Call API for a US Zipcode
                    WeatherData weatherData = OpenWeatherAPI.callCityCountryAPI((args[1]), "US");
                    printWeatherData(event, weatherData);
                }
                else {                              //Call for a generic city name
                    WeatherData weatherData = OpenWeatherAPI.callCityAPI(args[1]);
                    printWeatherData(event, weatherData);
                }
            }
            else if (args.length == 3){
                if (zipCodeIndex == 1){             //Call API for a Zipcode with a Country code
                    WeatherData weatherData = OpenWeatherAPI.callZipcodeCountryAPI(args[1], args[2]);
                    printWeatherData(event, weatherData);
                }
                else if (isUS_State(args[2])){      //Call for a City with a US State
                    String state = args[2];
                    state = state.replace(" ", "");
                    WeatherData weatherData = OpenWeatherAPI.callCityStateCountryAPI(args[1], state, "USA");
                    printWeatherData(event, weatherData);
                }
                else {                              //Call for a city with a Country code
                    event.respond("No city + country command yet");
                    WeatherData weatherData = OpenWeatherAPI.callCityCountryAPI(args[1], args[2]);
                    printWeatherData(event, weatherData);
                }
            }
            else{
                event.respond("!Weather command not recognized...");
            }*/
            //===========================================================================================================
        }
    }

    private static void printWeatherData(MessageEvent event, WeatherData weatherData) {
        if (weatherData.weatherID < 0){
            event.respondWith(Colors.ITALICS+Colors.RED+"Open weather could not find the location you entered... (bad request error)");
        }
        else {
            Vector<String> stringData = weatherData.dataDescription();
            event.respondWith(Colors.BOLD+Colors.UNDERLINE+Colors.DARK_BLUE+"Weather for: "+weatherData.origSearchString);
            for (String stringDatum : stringData) {
                event.respondWith(Colors.BLUE+Colors.BOLD+stringDatum);
            }
        }
    }

    public void onConnect(ConnectEvent event){
        System.out.printf("\nSuccessfully connected to server \"%s\"\n", event.getBot().getServerHostname());
    }
    public void onDisconnect(DisconnectEvent event){
        System.out.printf("\nDisconnected from server \"%s\"\n", event.getBot().getServerHostname());
    }
}
