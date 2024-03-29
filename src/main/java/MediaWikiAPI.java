//====================================================================================================================================================================
// Name        : MediaWikiAPI.java
// Author      : Jonathan Wachholz (JHW190002)
// Course	   : UTDallas CS 2336.501 Spring
// Version     : 1.0
// Copyright   : March. 2020
// Description :
//   Static class that calls the english wikipedia.org api to get information about a search
//          The callSearchAPI(String searchString, int searchLimit) method makes a search query for the given search string on
//              the wikipedia server. The search limit is the amount of search queries to return, so
//              callSearchAPI("Java", 5) would return a WikiPageInfo Vector with the first 5 search results for "Java"
//
//          The callPageSummaryAPI(int pageID, int numSentences) method will return a WikiPageInfo object with the first x
//              amount of sentences from the given Wikipages introduction / summary.
//
//====================================================================================================================================================================
//      The following information is just documentation on how the MediaWikiAPI works and ways to make the api calls
//====================================================================================================================================================================
//The pageid is the MediaWiki's internal article ID. You can use the action API's info property to get the full URL from pageid:
//https://en.wikipedia.org/w/api.php?action=query&prop=info&pageids=18630637&inprop=url&format=json
//
//You can just use a URL like this:
//http://en.wikipedia.org/?curid=18630637
//http://en.wikipedia.org/wiki?curid=18630637
//http://en.wikipedia.org/wiki/Translation?curid=18630637
//http://en.wikipedia.org/w/index.php?curid=18630637
//Note that MediaWiki ignores the page title if you specify a curid, so even
//http://en.wikipedia.org/wiki/FooBar?curid=18630637
//leads to the same page
//
//GETS FULL PAGE INFO/SUMMARY already parsed
// :https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&pageids=909036&redirects=1&exsentences=6&exlimit=1&exintro=1&explaintext=1&exsectionformat=plain
// :https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&titles=Elon_Musk&redirects=1&exsentences=6&exlimit=1&exintro=1&explaintext=1&exsectionformat=plain
//====================================================================================================================================================================
import java.util.Objects;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONObject;

public class MediaWikiAPI {
    static String  urlString;

    private static Vector<WikiPageInfo> parseMediaWikiSearch(JSONObject jsonObj1){
        JSONObject jsonObj2;
        JSONArray jsonArray;
        Vector<WikiPageInfo> wikiPages = new Vector<>(); //Creates default vector of size 10
        String pageTitle;
        int pageID;

        jsonObj2 = jsonObj1.getJSONObject("query");
        if (0 == jsonObj2.getJSONObject("searchinfo").getInt("totalhits")){
            WikiPageInfo wikiPageInfo = new WikiPageInfo();
            wikiPageInfo.setTitle("N/a");
            wikiPageInfo.setPageID(0);
            wikiPageInfo.setSummary("No results found");
            wikiPageInfo.setUrl("N/a");
            wikiPages.add(wikiPageInfo);
            return wikiPages;
        }
        if (jsonObj2.has("search")){
            jsonArray = jsonObj2.getJSONArray("search");
            for (int i = 0; i < jsonArray.length(); i++){
                jsonObj2 = jsonArray.getJSONObject(i);
                pageTitle = jsonObj2.getString("title");
                pageID = jsonObj2.getInt("pageid");
                WikiPageInfo myPage = MediaWikiAPI.callPageSummaryAPI(pageID, 5);
                wikiPages.add(new WikiPageInfo(pageTitle, pageID));
                wikiPages.get(i).setSummary(myPage.summary);
                /*wikiPages.add(new WikiPageInfo(jsonArray.getJSONObject(i).getString("title"), jsonArray.getJSONObject(i).getInt("pageid")));*/
            }
        }
        return wikiPages;
    }
    public static WikiPageInfo callPageSummaryAPI(int pageID, int numSentences) {
        urlString = String.format("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&pageids=%d&redirects=1&exsentences=%d&exlimit=1&exintro=1&explaintext=1&exsectionformat=plain", pageID, numSentences); // http:// has to be at the front of the url
        return parseWikiPageSummary(Objects.requireNonNull(CallAPIRequest.toJSONobj(urlString)), pageID);
    }
    private static WikiPageInfo parseWikiPageSummary(JSONObject jsonObj, int pageID) {
        WikiPageInfo pageInfo = new WikiPageInfo();
        try{
            pageInfo.setSummary(jsonObj.getJSONObject("query").getJSONObject("pages").getJSONObject(Integer.toString(pageID)).getString("extract"));
        }catch (Exception e){
            pageInfo.setSummary("Could not retrieve page summary...");
        }
        return pageInfo;
    }

    private static Vector<WikiPageInfo> callSearchAPI(){
        return parseMediaWikiSearch(Objects.requireNonNull(CallAPIRequest.toJSONobj(urlString)));
    }
    public static Vector<WikiPageInfo> callSearchAPI(String searchString, int searchLimit) {
        urlString = String.format("https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=%s&srlimit=%d", searchString, searchLimit); // http:// has to be at the front of the url
        return callSearchAPI();
    }
}
