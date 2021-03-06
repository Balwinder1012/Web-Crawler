import java.io.IOException;
import java.util.LinkedList;
import org.jsoup.*;
import org.jsoup.select.*;
import org.jsoup.nodes.*;
public class SpiderLeg
{
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    
    private LinkedList<String> links = new LinkedList<String>();
    private LinkedList<String> finalLinks = new LinkedList<String>();
    
    private Document htmlDocument;
    
    private String mainUrl="";

    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. Perform a searchForWord after the successful crawl
     * 
     * @param url
     *            - The URL to visit
     * @return whether or not the crawl was successful
     */
    
    
    
    public SpiderLeg(String mainUrl){
    	
    	this.mainUrl = mainUrl;
    
    	
    	
    	
    }
    
	public boolean crawl(String url)
    {
    	
    	
    	
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
         
            htmlDocument = connection.get();

            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                                                          // indicating that everything is great.
            {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                links.add(link.absUrl("href"));
            }
            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return false;
        }
    }


    /**
     * Performs a search on the body of on the HTML document that is retrieved. This method should
     * only be called after a successful crawl.
     * 
     * @param searchWord
     *            - The word or string to look for
     * @return whether or not the word was found
     */
    public boolean searchForWord(String searchWord)
    {
        // Defensive coding. This method should only be used after a successful crawl.
        if(htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        System.out.println("Searching for the word " + searchWord + " ...");
        String bodyText = htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }


    public LinkedList<String> getLinks()
    {
    	
    	onlyRelativeUrls();
    	
    	
        return finalLinks;
    }
    
void onlyRelativeUrls(){
    	
	    String sub = mainUrl.substring(7,9);
    	
    	String[] sites = links.toArray(new String[links.size()]);
    	
    	
    	for(String s: sites){
    		try{
    		
    	
    		
    		
    		
    			
    			if((s.length() - mainUrl.length()) >2)
    			   
    				if(!s.endsWith("jpg")){
    					
    					//if(sub1.equals(sub) ){
    			    		if(s.startsWith("http://")  )
    			    		{
    			    			String sub1 = s.substring(7,9);
    			    			if(sub1.equals(sub)){
    					
    			    				finalLinks.add(s);
    			    			}
    			    			
    					    }
    			    		if(s.startsWith("https://")){
    			    			
    			    			String sub1 = s.substring(8,10);
    			    			if(sub1.equals(sub)){
    		    					
    			    				finalLinks.add(s);
    			    			}
    			    			
    			    			
    			    		}
    					
    			
    			
    					
    				}
    						
    		
    		}
    		
    	
    	
		catch(Exception e){
			
			System.out.println(s);
		}
    		
    	
    }
    	
    	
}
}