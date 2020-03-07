


//https://openweathermap.org/current
/*
api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}
api.openweathermap.org/data/2.5/weather?q={city name},{state}&appid={your api key}
api.openweathermap.org/data/2.5/weather?q={city name},{state},{country code}&appid={your api key}

How to get icon URL
For code 501 - moderate rain icon = "10d"
URL is
http://openweathermap.org/img/wn/10d@2x.png

*/
/*By ZIP code
Description:
Please note if country is not specified then the search works for USA as a default.

API call:
api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}&appid={your api key}
Examples of API calls:
api.openweathermap.org/data/2.5/weather?zip=94040,us*/

import org.json.JSONArray;
import org.json.JSONObject;

public class OpenWeatherAPI{
    private static String urlString;

    private static WeatherData parseOpenWeatherAPI(JSONObject jsonObj){

        JSONObject jsonObj2;
        JSONArray jsonArray;
        WeatherData weatherData = new WeatherData();

        if (jsonObj == null){
            weatherData.weatherID = -400;
            return weatherData;
        }

        if (jsonObj.has("cod"))
            if (jsonObj.getInt("cod") == 404){
                weatherData.weatherID = -404;
                return weatherData;
            }

        jsonArray = jsonObj.getJSONArray("weather");
            jsonObj2 = jsonArray.getJSONObject(0);
                weatherData.weatherDesc = jsonObj2.getString("description");
                weatherData.weatherCategory = jsonObj2.getString("main");
                weatherData.weatherID = jsonObj2.getInt("id");
                weatherData.iconID = jsonObj2.getString("icon");

        jsonObj2 = jsonObj.getJSONObject("main");
            weatherData.avgTemp = jsonObj2.getFloat("temp");
            weatherData.tempFeels = jsonObj2.getFloat("feels_like");
            weatherData.temp_min = jsonObj2.getFloat("temp_min");
            weatherData.temp_max = jsonObj2.getFloat("temp_max");
            weatherData.humidity = jsonObj2.getInt("humidity");
            weatherData.pressure = jsonObj2.getInt("pressure");

        weatherData.visibility = jsonObj.getInt("visibility");

        if (jsonObj.has("rain")){
            jsonObj2 = jsonObj.getJSONObject("rain");
            if (jsonObj2.has("1h")){
                weatherData.rainMM1h = jsonObj2.getFloat("1h"); }
            if (jsonObj2.has("3h")){
                weatherData.rainMM3h = jsonObj2.getFloat("3h"); }
        }

        if (jsonObj.has("snow")){
            jsonObj2 = jsonObj.getJSONObject("snow");
            if (jsonObj2.has("1h")){
                weatherData.snowMM1h = jsonObj2.getFloat("1h"); }
            if (jsonObj2.has("3h")){
                weatherData.snowMM3h = jsonObj2.getFloat("3h"); }
        }
        if (jsonObj.has("clouds")){
            jsonObj2 = jsonObj.getJSONObject("clouds");
            if (jsonObj.has("all")){
                weatherData.percentClouds = jsonObj2.getInt("all"); }
        }

        weatherData.locationName = jsonObj.getString("name");
        weatherData.locationID = jsonObj.getInt("id");

        jsonObj2 = jsonObj.getJSONObject("sys");
        weatherData.countryName = jsonObj2.getString("country");

        return weatherData;
    }

    private static WeatherData callAPI(){
        return parseOpenWeatherAPI(CallAPIRequest.toJSONobj(urlString));
    }
    public static WeatherData callCityAPI(String cityName) {
        urlString = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&units=imperial&appid=%s", cityName ,TOKENS.getOpenWeatherAPIKEY());
        return callAPI();
    }
    public static WeatherData callCityStateCountryAPI(String cityName, String state, String country) {
        /*api.openweathermap.org/data/2.5/weather?q={city name},{state},{country code}&appid={your api key}*/
        urlString = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,%s,%s&units=imperial&appid=%s", cityName, state, country ,TOKENS.getOpenWeatherAPIKEY());
        return callAPI();
    }
    public static WeatherData callCityCountryAPI(String cityName, String countryCode) {
        /*ex. api.openweathermap.org/data/2.5/weather?q=London,uk*/
        urlString = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,%s&units=imperial&appid=%s", cityName, countryCode ,TOKENS.getOpenWeatherAPIKEY());
        return callAPI();
    }
    public static WeatherData callZipcodeAPI(String zipCode){ //Defaults to US states
        /*api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}&appid={your api key}*/
        urlString = String.format("http://api.openweathermap.org/data/2.5/weather?zip=%s&units=imperial&appid=%s", zipCode,TOKENS.getOpenWeatherAPIKEY());
        return callAPI();

    }
    public static WeatherData callZipcodeCountryAPI(String zipCode, String countryCode){
        urlString = String.format("http://api.openweathermap.org/data/2.5/weather?zip=%s,%s&units=imperial&appid=%s", zipCode, countryCode,TOKENS.getOpenWeatherAPIKEY());
        return callAPI();

    }


}
