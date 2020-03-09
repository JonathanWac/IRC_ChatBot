import com.google.common.base.MoreObjects;

import java.util.Vector;

public class WeatherData {
    public String weatherCategory, weatherDesc, locationName, iconID, countryName, origSearchString;
    public int locationID, visibility, humidity, pressure, weatherID, percentClouds;
    public float rainMM1h, rainMM3h, snowMM1h, snowMM3h, avgTemp, tempFeels, temp_min, temp_max;

    public static int instCount = 0;
    
    WeatherData(){
        instCount++;
    }

    public Vector<String> dataDescription(){
        Vector<String> myData = new Vector<>();
        String formatStr;

        formatStr = String.format("\tData from: %s, %s", locationName, countryName);
        myData.add(formatStr);

        formatStr = String.format("\tTemp - Avg %.2f\u2109, High %.2f\u2109, Low %.2f\u2109, Feels Like %.2f\u2109,", avgTemp, temp_max, temp_min, tempFeels);
        myData.add(formatStr);
        /*formatStr = String.format("\tAvg Temp %.2f\u2109", avgTemp);
        myData.add(formatStr);
        formatStr = String.format("\tTemp Max %.2f\u2109", temp_max);
        myData.add(formatStr);
        formatStr = String.format("\tTemp Min %.2f\u2109", temp_min);
        myData.add(formatStr);
        formatStr = String.format("\tFeels like %.2f\u2109", temp_min);
        myData.add(formatStr);
        formatStr = String.format("\t\tDescription: %s", weatherDesc);
        myData.add(formatStr);*/

        if (weatherCategory.equals("Rain") || weatherCategory.equals("Drizzle") || weatherCategory.equals("Thunderstorm")){
            formatStr = String.format("\tDescription: %s\tHourly Rain %.2fmm\tCloud coverage %d%%", weatherDesc, rainMM1h, percentClouds);
            myData.add(formatStr);
        }
        else if (weatherCategory.equals("Snow")){
            formatStr = String.format("\tDescription: %s\tHourly Snow %.2fmm\tCloud coverage %d%%", weatherDesc, snowMM1h, percentClouds);
            myData.add(formatStr);
        }else {
            formatStr = String.format("\tDescription: %s\tCloud coverage %d%%", weatherDesc, percentClouds);
            myData.add(formatStr);
        }
        return myData;
    }

    public String displayWeather(){
        return String.format("%s, %s\t%s\n%s\t\n avgTemp: %.2f\t\n highTemp: %.2f\t\n lowTemp: %.2f",locationName, countryName, weatherCategory, weatherDesc, avgTemp, temp_max, temp_min);
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("weatherCategory", weatherCategory)
                .add("weatherDesc", weatherDesc)
                .add("locationName", locationName)
                .add("iconID", iconID)
                .add("countryName", countryName)
                .add("locationID", locationID)
                .add("visibility", visibility)
                .add("humidity", humidity)
                .add("pressure", pressure)
                .add("weatherID", weatherID)
                .add("percentClouds", percentClouds)
                .add("rainMM1h", rainMM1h)
                .add("rainMM3h", rainMM3h)
                .add("snowMM1h", snowMM1h)
                .add("snowMM3h", snowMM3h)
                .add("avgTemp", avgTemp)
                .add("tempFeels", tempFeels)
                .add("temp_min", temp_min)
                .add("temp_max", temp_max)
                .toString();
    }
}

