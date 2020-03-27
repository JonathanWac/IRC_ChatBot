//====================================================================================================================================================================
// Name        : CallAPIRequest.java
// Author      : Jonathan Wachholz (JHW190002)
// Course	   : UTDallas CS 2336.501 Spring
// Version     : 1.0
// Copyright   : March. 2020
// Description :
//   Static class used by other classes to make any API call.
//      Contains 2 methods:
//          toString & toJSONobj
//          Both methods take a URL that is then used to make the API call:
//              The toString method returns the API request info in a String format
//                  that can be easily displayed in the System Console
//              The toJSONobj method returns the API request info in a org.json.JSONObject format
//                  to then be parsed separately by the other classes calling this method
//====================================================================================================================================================================

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class CallAPIRequest {
    public static String toString(String urlString){

        HttpGet apiClientRequest;
        CloseableHttpResponse apiClientResponse;

        CloseableHttpClient myClient = HttpClients.createDefault();     //Creates the client on our side that will send out our API Requests
        try{
            apiClientRequest = new HttpGet(urlString);
            apiClientResponse = myClient.execute(apiClientRequest);
            //Pack stored in Json format w/ charset=utf-8; Chunked: false;
            HttpEntity apiPackReceived = apiClientResponse.getEntity();
            return EntityUtils.toString(apiPackReceived);
        }
        catch (IOException e){
            System.err.println("#############################################################");
            System.err.println("An error occurred with accessing the API at url: \n\t"+urlString);
            e.printStackTrace();
            System.err.println("#############################################################");
        }
        return null;
    }

    public static JSONObject toJSONobj(String urlString){

        HttpGet apiClientRequest;
        CloseableHttpResponse apiClientResponse;
        CloseableHttpClient myClient = HttpClients.createDefault();     //Creates the client on our side that will send out our API Requests
        try{
            apiClientRequest = new HttpGet(urlString);
            apiClientResponse = myClient.execute(apiClientRequest);
            int statusCode = apiClientResponse.getStatusLine().getStatusCode();
            if (statusCode == 400){
                return null;
            }
            //Pack stored in Json format w/ charset=utf-8; Chunked: false;
            HttpEntity apiPackReceived = apiClientResponse.getEntity();
            String jsonString = EntityUtils.toString(apiPackReceived);
            JSONObject myJSONObj = new JSONObject(jsonString);
            myClient.close();
            return myJSONObj;
        }
        catch (IOException e){
            System.err.println("#############################################################");
            System.err.println("An error occurred with accessing the API at url: \n\t"+urlString);
            e.printStackTrace();
            System.err.println("#############################################################");
        }finally {
            try {
                myClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
