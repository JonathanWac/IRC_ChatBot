//====================================================================================================================================================================
// Name        : MapQuestAPI.java
// Author      : Jonathan Wachholz (JHW190002)
// Course	   : UTDallas CS 2336.501 Spring
// Version     : 1.0
// Copyright   : March. 2020
// Description :
//   Static class that calls the MapQuest.com API in order to parse the Longitude and Latitude data from a given search term
//          !Important to note, an invalid search term will always return the longitude / latitude data for Stockton Ohio
//              Primarily uses the call_LatLongAPI(String searchString) method to call the MapQuest API with the given search string
//                  And returns a vector with the Lat/Long data contained inside
//====================================================================================================================================================================
//
// https://www.mapquestapi.com/geocoding/v1/address?key={KEY}&inFormat=kvp&outFormat=json&location={Denver%2C+CO}&thumbMaps=true&maxResults=1
//
//
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Objects;
import java.util.Vector;


public class MapQuestAPI {
    static String urlString;

    public static Vector<Float> parseLatLong(JSONObject jsonObj){
        Vector<Float> latAndLongData = new Vector<>(2, 2);

        JSONObject jsonObj2;
        JSONArray jsonArray;

        if (jsonObj.has("results")){
            jsonArray = jsonObj.getJSONArray("results");
            jsonObj2 = jsonArray.getJSONObject(0);

            jsonArray = jsonObj2.getJSONArray("locations");
            jsonObj2 = jsonArray.getJSONObject(0);

            //String mapURL = jsonObj2.getString("mapUrl");
            jsonObj2 = jsonObj2.getJSONObject("latLng");

            latAndLongData.add(jsonObj2.getFloat("lat"));
            latAndLongData.add(jsonObj2.getFloat("lng"));
        }
        else {
            latAndLongData.add(Float.MAX_VALUE);
        }

        return latAndLongData;
    }
    private static Vector<Float> call_LatLongAPI(){
        return parseLatLong(Objects.requireNonNull(CallAPIRequest.toJSONobj(urlString)));
    }
    public static Vector<Float> call_LatLongAPI(String searchString){
        //searchString = searchString.trim().replace(",", "%2C");
        searchString = searchString.replaceAll(" +", "+");


        urlString = String.format("https://www.mapquestapi.com/geocoding/v1/address?" +
                        "key=%s&inFormat=kvp&outFormat=json&location=%s&thumbMaps=true&maxResults=1",
                            TOKENS.getMapQuestAPIKEY(), searchString);
        return call_LatLongAPI();
    }

}
