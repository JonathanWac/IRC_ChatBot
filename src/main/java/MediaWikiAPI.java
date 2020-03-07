//The pageid is the MediaWiki's internal article ID. You can use the action API's info property to get the full URL from pageid:
//https://en.wikipedia.org/w/api.php?action=query&prop=info&pageids=18630637&inprop=url&format=json

//You can just use a URL like this:
//http://en.wikipedia.org/?curid=18630637
//http://en.wikipedia.org/wiki?curid=18630637
//http://en.wikipedia.org/wiki/Translation?curid=18630637
//http://en.wikipedia.org/w/index.php?curid=18630637
//Note that MediaWiki ignores the page title if you specify a curid, so even
//http://en.wikipedia.org/wiki/FooBar?curid=18630637
//leads to the same page

//Oh, and you can also get the full page URL in your initial API call if you add "&prop=info&inprop=url":
//http://en.wikipedia.org/w/api.php?action=query&generator=search&gsrsearch=meaning&srprop=size%7Cwordcount%7Ctimestamp%7Csnippet&prop=info&inprop=url


import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONObject;

//Testing calls
//https://en.wikipedia.org/wiki/Special:ApiSandbox#action=query&format=json&list=search&srsearch=Java
public class MediaWikiAPI {
    static String  urlString;

    private static Vector<WikiPageInfo> parseMediaWikiSearch(JSONObject jsonObj1){
        JSONObject jsonObj2;
        JSONArray jsonArray;
        Vector<WikiPageInfo> wikiPages = new Vector<>(); //Creates default vector of size 10
        String pageTitle;
        int pageID;

        jsonObj2 = jsonObj1.getJSONObject("query");
        if (jsonObj2.has("search")){
            jsonArray = jsonObj2.getJSONArray("search");
            for (int i = 0; i < jsonArray.length(); i++){
                jsonObj2 = jsonArray.getJSONObject(i);
                pageTitle = jsonObj2.getString("title");
                pageID = jsonObj2.getInt("pageid");
                wikiPages.add(new WikiPageInfo(pageTitle, pageID));
                /*wikiPages.add(new WikiPageInfo(jsonArray.getJSONObject(i).getString("title"), jsonArray.getJSONObject(i).getInt("pageid")));*/
            }
        }

        return wikiPages;
    }

    private static Vector<WikiPageInfo> callAPI(){
        return parseMediaWikiSearch(CallAPIRequest.toJSONobj(urlString));
    }
    public static Vector<WikiPageInfo> callAPI(String searchString) {
        urlString = String.format("https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=%s", searchString); // http:// has to be at the front of the url
        return callAPI();
    }
}
