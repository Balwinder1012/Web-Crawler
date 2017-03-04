import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.IntDict;
import processing.core.*;

public class GUI extends Main {

	public JFrame frame;
	public JButton graph;
	public JButton urlList;
	public JButton flowingWords;
	public JButton submit;
	public JTextField url;
	public JTextField word;
	public JLabel A;
	public JLabel B;
	public static String WORD;
	public static String URL;
	
	public GUI(){
		
		frame = new JFrame();
		  frame.setSize(new Dimension(500,100));
		  frame.setLayout(new FlowLayout());
		  A = new JLabel("Enter Url:   ");
		  frame.add(A);
		  url = new  JTextField("http://jmit.ac.in/");
		  url.setPreferredSize(new Dimension(150,20));
		  frame.add(url);
		  B = new JLabel("Enter Word:  ");
		  
		  frame.add(B);
		  word = new JTextField("jmit");
		  word.setPreferredSize(new Dimension(150,20));
		  frame.add(word);

		  WORD = word.getText();
		  URL = url.getText();
		  
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
		  
		  frame.setVisible(true);
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  
		  
		  //Action Listener
		  
		  submit.addActionListener(
				  new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						
						  check=true;
						  spider = new Spider();
					      spider.search(URL,WORD);
					 	  submit.setText("Crawled");
					      submit.setEnabled(false);
					      urls = spider.getUrls();
					      arrayOfUrls = new String[urls.size()];
							arrayOfUrls =  urls.toArray(arrayOfUrls);
							callLoadData();			
							//loadData();	
						     check=false;
						     

						    urlList.setEnabled(true);
						     
					}
				
		  });
		  
		  
		  
		  urlList.addActionListener(
				  new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						
						
			
				        			
		                select =1;		
		                flowingWords.setEnabled(true);
		                graph.setEnabled(true);
		      

					}
				
		  });
		  
		  graph.addActionListener(
				  new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						loadGraphData();
					
						select = 2;

						  				}
				
		  });
		  
		  
		  flowingWords.addActionListener(
				  new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						select = 3;

						  
					}
				
		  });
		  
		  
		  
		  
		  
		  
	}
	
	 public  static String getUrl()
	 {  return(URL);
	 
	 
	 }
	 public static String getWord(){
		 
		 return(WORD);
	 }
	 
	public void callLoadData(){
		
		super.loadData();
		
	}
	
}
