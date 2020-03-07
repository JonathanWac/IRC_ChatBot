import de.tudarmstadt.ukp.wikipedia.api.DatabaseConfiguration;
import de.tudarmstadt.ukp.wikipedia.api.Page;
import de.tudarmstadt.ukp.wikipedia.api.WikiConstants;
import de.tudarmstadt.ukp.wikipedia.api.Wikipedia;
import de.tudarmstadt.ukp.wikipedia.api.exception.WikiApiException;
import de.tudarmstadt.ukp.wikipedia.api.exception.WikiInitializationException;

public class WikipediaAPI {

    public static void callAPI(String searchTerm) {

        // configure the database connection parameters
        /*DatabaseConfiguration dbConfig = new DatabaseConfiguration();
        String urlString = "https://en.wikipedia.org/w/api.php?";
        dbConfig.setHost(urlString);
        dbConfig.setDatabase("DATABASE");
        dbConfig.setUser("USER");
        dbConfig.setPassword("PASSWORD");
        dbConfig.setLanguage(WikiConstants.Language.english);

        // Create a new German wikipedia.
        Wikipedia wiki = new Wikipedia(dbConfig);

        // Get the page with title "Hello world".
        // May throw an exception, if the page does not exist.
        Page page = wiki.getPage("Hello world");
        System.out.println(page.getText());*/

    }


}
