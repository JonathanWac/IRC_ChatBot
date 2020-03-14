import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Vector;

//https://www.mapquestapi.com/geocoding/v1/address?key={KEY}&inFormat=kvp&outFormat=json&location={Denver%2C+CO}&thumbMaps=true&maxResults=1
//
//
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
