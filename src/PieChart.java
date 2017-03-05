
import java.io.File;
import java.io.IOException;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartUtilities; 

public class PieChart {

	public PieChart(int[] values,String[] keys,int n) throws IOException{
		
		final DefaultPieDataset ds = new DefaultPieDataset();
		
		for(int i=0;i<n;i++)
		ds.setValue(keys[i], new Double(values[i]));
		
		
		
		 JFreeChart Chart = ChartFactory.createPieChart(
				 
				 "Pie Chart",
				 ds,
				 true,true,false
				 
				 
				 );
		 
		 int width = 600; /* Width of the image */ 
	      int height = 600; /* Height of the image */  
	
	      File pieChart = new File( "PieChart.jpeg" );  
	      ChartUtilities.saveChartAsPNG(pieChart,Chart, width, height);
	   }

	
	} 
		 
		
		
		
		
		
	
	
	

