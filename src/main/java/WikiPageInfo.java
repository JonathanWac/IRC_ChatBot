public class WikiPageInfo {
    String title, url;
    int pageID;

    public WikiPageInfo(){

    }

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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public int getPageID() { return pageID; }
    public void setPageID(int pageID) { this.pageID = pageID; }
}
