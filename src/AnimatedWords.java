


public class AnimatedWords extends Main{

	public AnimatedWords(){
		
		
		
    	

   	 // Look at words one at a time
   	
   		 
   	 if (super.counter < super.tokens.length) {
   		 String s = super.tokens[counter];
   	    super.counter++;
   	    super.concordance.increment(s);
   	       
   	  }
   	

   	  // x and y will be used to locate each word
   	  float x = 0;
   	  float y = 48;

   	 super.concordance.sortValues();

   	  String[] keys =  super.concordance.keyArray();

   	  // Look at each word
   	  for (String word :  keys) {
   	    int count =  super.concordance.get(word);

   	    // Only display words that appear 3 times
   	    if (count > 3) {
   	      // The size is the count
   	      int fsize = constrain(count, 0, 48);
   	      textSize(fsize);
   	     if( super.WORD.equals(word))
   	     {
   	    	 fill(0,0,255);
   	    	
   	    	 strokeWeight(2);

   	    	 text(word, x, y);
   	     }
   	     else{
   	    	 
   	      	
   	      fill(0,0,0);
 
   	      strokeWeight(1);
   	      text(word, x, y);
   	   super.ginti++;
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
   	    	 super.select = 4;
   	    	  break;
   	         
   	      }
   	    }
   	  } 
	
		
		
	}
	
	
}
