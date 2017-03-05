
import java.util.*;


import processing.core.PApplet;
public class Spider extends PApplet{

	 static final int MAX_PAGES_TO_SEARCH = 10;
	 
	 
     Set<String> pagesVisited = new HashSet<String>();
     List<String> pagesToVisit = new LinkedList<String>();
	 
     int counter =0;
	 String[] allWords;
	 ArrayList<String> urls = new ArrayList<String>();

   String nextUrl()
    {
        String nextUrl;
        do
        {
            nextUrl = pagesToVisit.remove(0);
        } while(pagesVisited.contains(nextUrl));
        pagesVisited.add(nextUrl);
      
        return nextUrl;
    }
	
    public void search(String url, String searchWord)
    {
        while(pagesVisited.size() < MAX_PAGES_TO_SEARCH)
        {
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();
            if(pagesToVisit.isEmpty())
            {
                currentUrl = url;
                pagesVisited.add(url);
            
            }
            else
            {
                currentUrl = nextUrl();
            }
            boolean sucessfullyCrawled = leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
                                   // SpiderLeg
            if(sucessfullyCrawled){
            boolean success = leg.searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                counter++;
                urls.add(currentUrl);
            }
            pagesToVisit.addAll(leg.getLinks());
            
            }
        }
        System.out.println(String.format("**Done** Visited %s web page(s), Word found " + counter + " no. of times", pagesVisited.size()));
    }
    
    ArrayList<String> getUrls(){
    	
    	return(urls);
    }
    
}
