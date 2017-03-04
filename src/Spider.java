
import java.util.*;


import processing.core.PApplet;
public class Spider extends PApplet{

	 static final int MAX_PAGES_TO_SEARCH = 10;
     Set<String> pagesVisited = new HashSet<String>();
     List<String> pagesToVisit = new LinkedList<String>();
	 int counter =0;
	// ArrayList<String> allWords = new ArrayList<String>();
	 String[] allWords;
	 ArrayList<String> urls = new ArrayList<String>();

   String nextUrl()
    {
        String nextUrl;
        do
        {
            nextUrl = this.pagesToVisit.remove(0);
        } while(this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
       // addWords(nextUrl);
        return nextUrl;
    }
	
    public void search(String url, String searchWord)
    {
        while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
        {
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl = url;
                this.pagesVisited.add(url);
            
            }
            else
            {
                currentUrl = nextUrl();
            }
            leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
                                   // SpiderLeg
            boolean success = leg.searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
               // break;
                counter++;
                urls.add(currentUrl);
            }
            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println(String.format("**Done** Visited %s web page(s), Word found " + counter + " no. of times", this.pagesVisited.size()));
    }
    
    ArrayList<String> getUrls(){
    	
    	return(urls);
    }
    
}
