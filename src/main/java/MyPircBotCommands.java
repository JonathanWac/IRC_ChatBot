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
                "Missouri", "MO", "Wyoming", "WY"};

        for (String s : US_States_Array) {
            if (s.toLowerCase().equals(string.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void onGenericMessage(GenericMessageEvent event){
        /*System.out.printf("\n!!!!%s!!!!\n", event.toString());
        System.out.printf("\n!!!!%s!!!!\n", event.getMessage());*/
    }
    public void onMessage(MessageEvent event){
        /*System.out.printf("\n!!!!%s!!!!\n", event.getChannelSource());
        System.out.printf("\n!!!!%s!!!!\n", event.getMessage());*/
        String msg = event.getMessage().trim();

        if (msg.startsWith("!Help") || msg.startsWith("!Commands")){
            event.respond("List of commands:");
            event.respond("!Weather 'zipcode' (US only) - !Weather 'city' (worldwide) - !Weather 'US_City' 'US_State' (US only) - !Weather city 3-Char_countrycode");
        }
        else if (msg.startsWith("!ADMIN")){
            String adminMsg = msg;
            adminMsg = adminMsg.replace(',', ' ');
            String[] args = adminMsg.split(" +");
            if (args[1].toLowerCase().equals("changech") && args.length >= 3){
                String channelName2 = args[2];
                event.respondWith("/join "+channelName2);
                event.getBot().send().joinChannel(channelName2);
                event.respondChannel("Test response");
                //Not yet implemented
                // Thought is to throw an exception back to main with the channel name, which would then reconnect the bot to a new channel
            }
            else if(args[1].toLowerCase().equals("exitch")){
                event.respondWith("Quitting because of command" +msg);
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
                        searchStringBuilder.append(args[i]);
                    }
                    WikiPageInfo[] results = MediaWikiAPI.callAPI(searchStringBuilder.toString()).toArray(new WikiPageInfo[0]);
                    for (int i = 0; i < results.length; i++){
                        event.respondWith(String.format("Result %d: %s\tURL: %s", i+1, results[i].getTitle(), results[i].getUrl()));
                    }
                }
                else if (args.length == 3){

                    WikiPageInfo[] results = MediaWikiAPI.callAPI(args[2]).toArray(new WikiPageInfo[0]);

                    for (int i = 0; i < results.length; i++){
                        event.respondWith(String.format("Result %d: %s\tURL: %s", i+1, results[i].getTitle(), results[i].getUrl()));
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

            ////////////////////////////////////////////////////////////////////////////
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
            ////////////////////////////////////////////////////////////////////////////////////////////
        }

    }

    private static void printWeatherData(MessageEvent event, WeatherData weatherData) {
        if (weatherData.weatherID < 0){
            event.respondWith("Open weather could not find the location you entered... (bad request error)");
        }
        else {
            Vector<String> stringData = weatherData.dataDescription();
            event.respondWith("Weather for: "+weatherData.origSearchString);
            for (String stringDatum : stringData) {
                event.respondWith(stringDatum);
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
