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

        for (int i = 0; i < US_States_Array.length; i++){
            if (US_States_Array[i].toLowerCase().equals(string.toLowerCase())){
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

        if (event.getMessage().startsWith("!Help") || event.getMessage().startsWith("!Commands")){
            event.respond("List of commands:");
            event.respond("\t!Weather 'zipcode' (US only) \t!Weather 'city' (worldwide) \t!Weather 'US_City' 'US_State' (US only) \t!Weather city 3-Char_countrycode");
        }
        else if (event.getMessage().startsWith("!Wiki")){
            event.respond("No command yet for wiki");
        }
        else if (event.getMessage().startsWith("!Weather")){
            String msg = event.getMessage();
                msg = msg.trim();
                msg = msg.replace(',', ' ');
            String[] args = msg.split(" +");

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
                    WeatherData weatherData = OpenWeatherAPI.callCityStateCountryAPI(args[1], args[2], "US");
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
            }
        }

    }

    private static void printWeatherData(MessageEvent event, WeatherData weatherData) {
        if (weatherData.weatherID < 0){
            event.respondWith("Open weather could not find the location you entered... (bad request error)");
        }
        else {
            Vector<String> stringData = weatherData.dataDescription();

            for (int i = 0; i < stringData.size(); i++){
                event.respondWith(stringData.get(i));
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
