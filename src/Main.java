import processing.core.PApplet;
import processing.core.PImage;
import processing.data.IntDict;

import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


import org.jsoup.*;


import javax.swing.*;

public class Main extends PApplet {

	
	//########################################################################################################################################
	
	//Variables used for GUI
	JFrame frame;
	JButton graph;
	JButton urlList;
	JButton flowingWords;
	JButton submit;
	JTextField url;
	JTextField word;
	JLabel A;
	JLabel B;
	static	PImage image;
	
	String[] lines;
	int select=0;
	String[] arrayOfUrls;
	int counter = 0;
	int x=0;
	int ginti=0;
	IntDict concordance;
	IntDict iD;
	
	static int process=0;
	
	int MAX_PAGES_TO_SEARCH= 10;
	//These flags are used for gui animation
	boolean check= false;
	boolean motionCheck = false;
	
	
	//This flag is used in loadGraphData 
	boolean check1 = true;
	//This flag is used to draw Load Graph only one time
	boolean onlyOneTime = true;
	
	String WORD="";
	String URL="";


	int[] EntryValues;  //used for loadGraphData();
	String[] EntryKeys;	
	int noOfGraphElem = 7;
	Spider spider;
	String[] tokens;
	ArrayList<String> urls = new ArrayList<String>();
	
	
	//#######################################################################################################################################
	
	
	
	
	public static void main(String[] args){
		PApplet.main("Main");	
	}
	

	public void settings(){
		size(600,600);		
	}
	
	
	public void setup(){
		
	  //GUI#######################################################################################################################################	
	  image = loadImage("C://Users//Saini//workspace//WebCrawler//src//wc.jpg");	
	  
	  frame = new JFrame();
	  frame.setSize(new Dimension(500,100));
	  frame.setLayout(new FlowLayout());
	  A = new JLabel("Enter Url:   ");
	  frame.add(A);
	  url = new  JTextField("http://");
	  
	  url.setPreferredSize(new Dimension(150,20));
	  frame.add(url);
	  B = new JLabel("Enter Word:  ");
	  
	  frame.add(B);
	  word = new JTextField();
	  word.setPreferredSize(new Dimension(150,20));
	  frame.add(word);
	//  WORD = word.getText();
	 
	  
	  System.out.println(URL + " " + WORD);
	  submit = new JButton("Crawl");
	  	
	  
	  frame.add(submit);
	  urlList = new JButton("URL list");
	  urlList.setEnabled(false);
	  frame.add(urlList);
	  
	  
	  
	  graph = new JButton("Graph");
	  graph.setEnabled(false);
	  frame.add(graph);
	 
	  flowingWords = new JButton("Word Flow");
	  frame.add(flowingWords);
	  flowingWords.setEnabled(false);
	  
	  //Event Handlers ######################################################################################################################################
	  
	  submit.addActionListener(
			  new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					
					  check=true;
					  URL = url.getText();
					  WORD = word.getText();
					  spider = new Spider(MAX_PAGES_TO_SEARCH);
				      spider.search(URL,WORD);
				      submit.setText("Crawled");
				      submit.setEnabled(false);
				      urls = spider.getUrls();
				      arrayOfUrls = new String[urls.size()];
					  arrayOfUrls =  urls.toArray(arrayOfUrls);
					  check=false;
					  motionCheck= true;
					  loadData();    
					  motionCheck= false;
					  urlList.setEnabled(true);
					     
				}
			
	  });

	  
	  urlList.addActionListener(
			  new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
			        			
	                select =1;		
	                flowingWords.setEnabled(true);
	                
	
				}
			
	  });
	  
	 
	 
	  graph.addActionListener(
			  new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						loadGraphData();
					} 
					catch (IOException e1) {
						
						e1.printStackTrace();
					}
					select = 2;

					  				}
			
	  }); 
	  
	 
	  flowingWords.addActionListener(
			  new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					graph.setEnabled(true);
					select = 3;

					  
				}
			
	  });
	 
     frame.setVisible(true);
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
     iD = new IntDict();
	

	}
	
	// Adding two String Arrays into one
	public String[] combine(String[] a,String[] b){
		
		String[] result = new String[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length , b.length);
		return result;
	}
	
	
	public  void loadData(){
	
		String[] urls= arrayOfUrls;
	
		lines = loadStrings(URL);
		
		for(int i=1;i<urls.length;i++)
		{ 
			String[] lines2 = loadStrings(urls[i]);
			lines = combine(lines,lines2); 	
		    
		}
		
		
		String allText = join(lines," ").toLowerCase();
		String parsedText = Jsoup.parse(allText).text();
		
       tokens = splitTokens(parsedText," @#$&*+-?><.,;:'\"");
	
       concordance = new IntDict();
       
       
       ArrayList<String> al = new ArrayList<String>();
       
       al = checkTokens();
       
       tokens = al.toArray(new String[al.size()]);
		
	}
	
	
	public ArrayList<String> checkTokens(){
		
		ArrayList<String> al = new ArrayList<String>();
		for(String t: tokens)
		{
			
			if(t.length()>3){
				if(checkNumsInWords(t)){   // It eliminates all the words containing number, We can add 3,4..9 to eliminate all 
				
				al.add(t);
			}
			}
			
			
		}
		
	return al;	
	}
	
	public boolean checkNumsInWords(String t){
		
		if((t.contains("0") || t.contains("1") ||  t.contains("2") ||
			t.contains("3") ||  t.contains("4")||  t.contains("5") ||
			t.contains("6") || t.contains("7")||  t.contains("5") ||
			t.contains("9")))
		return false;
		
		else	
		return true;
	}
	public void draw(){

	
	switch(select){
	
	 case 1: drawurlList(); break;
	
	
	 
	 case 3: drawflowingWords(); break; 
	 
	 case 4: pauseTheScreen(); break;
	
	 default: drawDefault();
	}	
		
		
	}
	
	public void pauseTheScreen(){
		
	
	}
	
	
    public void	drawflowingWords(){
   	
    	background(200);


    	 // Look at words one at a time
    	
    		 
    	 if (counter < tokens.length) {
    		 String s = tokens[counter];
    	    counter++;
    	    concordance.increment(s);
    	       
    	  }
    	

    	  // x and y will be used to locate each word
    	  float x = 0;
    	  float y = 48;

    	  concordance.sortValues();

    	  String[] keys = concordance.keyArray();

    	  // Look at each word
    	  for (String word : keys) {
    	    int count = concordance.get(word);

    	    // Only display words that appear 3 times
    	    if (count > 3) {
    	      // The size is the count
    	      int fsize = constrain(count, 0, 48);
    	      textSize(fsize);
    	     if(WORD.equals(word))
    	     {
    	    	 fill(0,0,255);
    	    	
    	    	 strokeWeight(2);

    	    	 text(word, x, y);
    	     }
    	     else{
    	    	 
    	      	
    	      fill(0,0,0);
  
    	      strokeWeight(1);
    	      text(word, x, y);
    	      ginti++;
    	     }
    	      // Move along the x-axis
    	      x += textWidth(word + " ");
    	    }

    	    // If x gets to the end, move y
    	    if (x > width) {
    	      x = 0;
    	      y += 48;
    	      // If y gets to the end, we're done
    	      if (y > height-24) {
    	          select = 4;
    	    	  break;
    	         
    	      }
    	    }
    	  } 
 	
    
    	
    }    	 
    
    public void loadGraphData() throws IOException{
    	 int i=0;
		    
    	 
    	 EntryValues = new int[noOfGraphElem];
    	 EntryKeys = new String[noOfGraphElem];
    	 concordance.sortValuesReverse(); 
		 
    	 for (String word : concordance.keyArray()) {
		    
		    int count = concordance.get(word);
		       
		    EntryValues[i] = count;
		    EntryKeys[i] = word;
		    //list.add(Entry);
		    
		    iD.set(word,EntryValues[i]);
		    i++;
		    if(i>=noOfGraphElem)
		    {
		      check1=false;
		     break;
		     
		    }
		    
		  }
    	 
    	    new PieChart(EntryValues,EntryKeys,noOfGraphElem);
    	    
    	    
    	    
    	    JFrame frame = new JFrame();
    	    frame.setSize(600,640);
    	   
    	    frame.add(new JLabel(new ImageIcon("PieChart.jpeg")));
    	    frame.setVisible(true);
    
    	    
		    
		    select=0;
    	
    }
	public void drawurlList(){
		
		
		
		background(134,155,30);
		 
 
    stroke(255,0,0);
    strokeWeight(3);
    
    textSize(12);
    
     
    int y=20;
    	for(int i=0;i < arrayOfUrls.length ; i++){
    		
    		text(arrayOfUrls[i],20,y);
            y+=20;
    	}
		
    	
  
	}
	
	//The Initial Canvas
	public void drawDefault(){
		image(image,0,0);
		
		int info = MAX_PAGES_TO_SEARCH;	
		String i = "Pages To Crawl : "+info;
		textSize(16);
		fill(0);
		text(i,20,30);	
			
			
		if(check){
			
		fill(255);
		stroke(0,0,255);
		rect(300,550,280,20);
	
		fill(0);	
		/*
		
		
		*/
		
		int X = (int)map(process,0,MAX_PAGES_TO_SEARCH,0,277);
		rect(303, 553 , X , 15);
	
		}
		
		if(motionCheck){

			fill(255);
			stroke(0,0,255);
			rect(300,550,280,20);
			fill(0);
			rect(303+x,553,15,15);
			
			if(303+x < width-40)
				x=x+2;
			else
				x=0;
		
			}
			
		}
	
	
	public void keyPressed() {
		  if (key == CODED) {
			  
		    if (keyCode == UP ) 
		    	MAX_PAGES_TO_SEARCH++; 
		    
		    if (keyCode == DOWN && MAX_PAGES_TO_SEARCH >1) 
		    	MAX_PAGES_TO_SEARCH--;
		     
		  } 
		}

	
	
}


