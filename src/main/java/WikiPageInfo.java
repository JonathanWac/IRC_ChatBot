//====================================================================================================================================================================
// Name        : WikiPageInfo.java
// Author      : Jonathan Wachholz (JHW190002)
// Course	   : UTDallas CS 2336.501 Spring
// Version     : 1.0
// Copyright   : March. 2020
// Description :
//   Container class that stores Info from a Wikipedia page
//          Contains:
//              The Page Title, the Page Summary (if added later), and the PageID which is then used to generate a shortened page URL
//====================================================================================================================================================================

public class WikiPageInfo {
    public String title, url, summary;
    public int pageID;

    public WikiPageInfo(){}

    public WikiPageInfo(String title, int pageID) {
        this.title = title;
        this.pageID = pageID;
        url = "http://en.wikipedia.org/?curid="+pageID;
    }

    public WikiPageInfo(String title, int pageID, String url) {
        this.title = title;
        this.url = url;
        this.pageID = pageID;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public int getPageID() { return pageID; }
    public void setPageID(int pageID) {
        this.pageID = pageID;
        url = "http://en.wikipedia.org/?curid="+pageID;
    }
}
