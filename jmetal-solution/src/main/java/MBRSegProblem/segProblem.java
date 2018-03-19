package MBRSegProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalLogger;

import com.github.davidmoten.rtree.geometry.Point;

import Chart.Paint;
import Rtree.Objects;
import Rtree.RandomObjects;
import Rtree.RectangleMBR;
import util.Const;

@SuppressWarnings("serial")
public class segProblem extends AbstractIntegerProblem{
	public int count=0;
	public Objects ranbojs;

    XYSeries mSeriestotalCross = new XYSeries("totalCross");
    XYSeries mSeriestotalSqure = new XYSeries("totalSqure");
    XYSeries mSeriestotalSimil = new XYSeries("totalSimil");
    XYSeries mSeriestotalVarian = new XYSeries("totalVarian");
	public segProblem(int numberOfVariables,Objects ranobjs) {
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(3);
		
	   // setNumberOfObjectives(3);
	    setName("segProblem");
	    this.ranbojs=ranobjs;
	    List<Integer> lowerLimit = new ArrayList<Integer>(getNumberOfVariables()) ;
	    List<Integer> upperLimit = new ArrayList<Integer>(getNumberOfVariables()) ;
		
	    for (int i = 0; i < getNumberOfVariables(); i++) {
	        lowerLimit.add(0);
	        upperLimit.add(Const.numberOfClass-1);
	       // upperLimit.add(100);
	      }
	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	}

	public void evaluate(IntegerSolution solution) {
		 double[] f = new double[getNumberOfVariables()];
		 int numberofvara=getNumberOfVariables();
		 
		 //构建jsonmap存储每一个类别的对象�?		 
		 
		 HashMap<Integer, RectangleMBR> jsomap = getjsonmap(numberofvara,solution,this.ranbojs);
		 int classNumber= jsomap.size();
		 float totalCross=0;
		 float totalSqure=0;
		 float totalSimil=0;
		 for(int i=0;i<classNumber;i++)
		 {
			 for(int j=i+1;j<classNumber;j++)
			 {
				 if((RectangleMBR)jsomap.get(i)!=null&&(RectangleMBR)jsomap.get(j)!=null)
				 {
				 totalCross=totalCross+RectangleMBR.crossSqureRect( (RectangleMBR)jsomap.get(i), (RectangleMBR) jsomap.get(j)); 
				 totalSqure=totalSqure+ RectangleMBR.totalSqureRect( (RectangleMBR)jsomap.get(i), (RectangleMBR) jsomap.get(j));
				 totalSimil=totalSimil+RectangleMBR.totalSimil((RectangleMBR)jsomap.get(i), (RectangleMBR) jsomap.get(j));
				 }
			}
		 }
		 
		 if(totalCross==0)
		 {
			 System.out.println("oute");
		 }
		// float testvalue=(solution.getVariableValue(0)-50)*(solution.getVariableValue(0)-50);
		 float totalVarian=variance(jsomap);
		f[0]=totalCross;
	//	f[1]=totalSqure;
		f[1]=totalVarian;
		f[2]=10-jsomap.size();
	//	f[2]=totalSimil;
	  solution.setObjective(0, f[0]);
	   solution.setObjective(1, f[1]);
	   solution.setObjective(2, f[2]);
	//   solution.setObjective(3, f[3]);
	    count++;
	    if(count/100==0){
	    	mSeriestotalCross.add(count, totalCross);
	    	mSeriestotalCross.add(count, 10-jsomap.size());
	    //	mSeriestotalSqure.add(count, totalSqure);
	    	mSeriestotalVarian.add(count, totalVarian);
	   
	  //mSeriestotalSimil.add(count, totalSimil);
	  
	    }
		  JMetalLogger.logger.info("Total totalCross: " + totalCross);
		 JMetalLogger.logger.info("Total tatalsqure: " + totalSqure);
		 JMetalLogger.logger.info("Total totalVarian: " + totalVarian);
		 JMetalLogger.logger.info("Total classNumber: " + classNumber);
//		  JMetalLogger.logger.info("Total totalSimil: " + totalSimil);
	      
	}
	
	
	public void paint()
	{
	    XYSeriesCollection mCollection = new XYSeriesCollection();
	    XYSeriesCollection mCollectiontotalVarian = new XYSeriesCollection();
	    
			//JFrame frame=new JFrame("Java数据统计�?");  
		   // frame.setLayout(new GridLayout(2,2,10,10));  
		    mCollection.addSeries(mSeriestotalCross);
		    mCollection.addSeries(mSeriestotalSqure);
		    mCollectiontotalVarian.addSeries(mSeriestotalVarian);
		 //   mCollection.addSeries(mSeriestotalCross);
		    Paint.paintXY(mCollection);
		    Paint.paintXY(mCollectiontotalVarian);
		    
	}

	public static HashMap<Integer, RectangleMBR> getjsonmap(int numberofvara, IntegerSolution solution,Objects temRanbojs)
	{
		HashMap<Integer, RectangleMBR> jsonmap= new HashMap<Integer, RectangleMBR>();
		 for(int i=0;i<numberofvara;i++)
		 {
					 int classkey=solution.getVariableValue(i);
					 //如果该类别的点没有在jsonmap中出�?
					 if(jsonmap.get(classkey)==null)
					 {
						 
						 Rtree.Object obj1=temRanbojs.randomobjects.get(i);	
						 Point pointtemp1=obj1.getPoint();
						 RectangleMBR ren= new RectangleMBR(pointtemp1.x1(), pointtemp1.y1(),pointtemp1.x1(), pointtemp1.y1());
						 String str1=obj1.getText();
						 ren.text=str1;
						 ren.arrRects.add(ren);
						 jsonmap.put(classkey, ren);	 
					 }
					 else{
						 //这里主要分了两个部分 ，首先对 空间对象进行分类加入 ，然后对空间对象 进行 MBR并合并text�?
						 Rtree.Object obj1=temRanbojs.randomobjects.get(i);	
						 Point pointtemp1=obj1.getPoint();
						 RectangleMBR ren1= new RectangleMBR(pointtemp1.x1(), pointtemp1.y1(),pointtemp1.x1(), pointtemp1.y1());
						 ren1.text=obj1.text;						 
						 RectangleMBR ren= (RectangleMBR) jsonmap.get(classkey);
						 String str1=ren.getText();
						 ren.accumulateRect(ren,temRanbojs.getObject(i).getPoint());
						 String str2=temRanbojs.randomobjects.get(i).getText();
						 ren.acumalateString(str1,str2);
						 ren.arrRects.add(ren1);
						 jsonmap.put(classkey, ren);
					 }
		 }
		
		return jsonmap;
	}

	public float variance(HashMap<Integer, RectangleMBR> jsomap){
		float varir=0;
		float arv=0;
		float total=Const.numberOfSpatialObjects;
	
		arv=total/jsomap.size();
		for(int i=0;i<Const.numberOfClass;i++)
		{
			if(jsomap.get(i)==null){
				continue;
				}
			
			else{
			varir=(float) (varir+Math.sqrt(Math.pow((jsomap.get(i).arrRects.size()-arv),2)));}
		}
		return varir;
	}
	
}
