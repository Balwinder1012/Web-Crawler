
import java.util.*;



public class Spider extends Main{

	 int MAX_PAGES_TO_SEARCH = 0;
	 
	 
     Set<String> pagesVisited = new HashSet<String>();
     List<String> pagesToVisit = new LinkedList<String>();
	 
     int counter =0;
	 String[] allWords;
	 ArrayList<String> urls = new ArrayList<String>();
	 
	 String searchWord= "";
	 String url="";
	 int processCounter = 0;
	
	 public Spider(int no){
		 MAX_PAGES_TO_SEARCH = no;
	 }
	 
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
    	this.url = url;
    	this.searchWord = searchWord;
        while(pagesVisited.size() < MAX_PAGES_TO_SEARCH)
        {
        	
            String currentUrl;
            SpiderLeg leg = new SpiderLeg(url);
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
                System.out.println();
                counter++;
                urls.add(currentUrl);
            }
            
            
            LinkedList<String>  ll = new LinkedList<String>();
            
            
            ll=leg.getLinks();
            
            /*
            System.out.println(ll.size());
            String test[] = new String[ll.size()];
            test = ll.toArray(new String[ll.size()]);
            for(String t: test)	
            System.out.print(t+" ");
            	
            */
        
            
            pagesToVisit.addAll(ll);
            
            }
           
            super.process++; 
        }
        System.out.println(String.format("**Done** Visited %s web page(s), Word found " + counter + " no. of times", pagesVisited.size()));
        
    }
    
    ArrayList<String> getUrls(){
    	return(urls);
    }
    
    
    
}
