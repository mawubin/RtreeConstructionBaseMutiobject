package MBRSeg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalLogger;

import com.github.davidmoten.rtree.geometry.Point;

import Rtree.RandomObjects;
import Rtree.RectangleMBR;

@SuppressWarnings("serial")
public class segProblem extends AbstractIntegerProblem{

	public RandomObjects ranbojs;
	public segProblem(int numberOfVariables,RandomObjects ranobjs) {
		setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(2);
	    setName("segProblem");
	    this.ranbojs=ranobjs;
	    List<Integer> lowerLimit = new ArrayList<Integer>(getNumberOfVariables()) ;
	    List<Integer> upperLimit = new ArrayList<Integer>(getNumberOfVariables()) ;
		
	    for (int i = 0; i < getNumberOfVariables(); i++) {
	        lowerLimit.add(0);
	        upperLimit.add(Const.numberOfClass);
	      }
	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	}



	public void evaluate(IntegerSolution solution) {
		// TODO Auto-generated method stub
		 double[] f = new double[getNumberOfVariables()];
		 int numberofvara=getNumberOfVariables();
		 RandomObjects temRanbojs= new RandomObjects(numberofvara);
		 temRanbojs=this.ranbojs;
		// JSONObject jso=new JSONObject();
		// Map<String,RandomObjects> jsomap= new HashMap();
		 HashMap<Integer, RectangleMBR> jsomap = new HashMap<Integer, RectangleMBR>();
		 for(int i=0;i<numberofvara;i++)
		 {
			 for(int j=i;j<numberofvara;j++)
			 {
				 if( Math.round(solution.getVariableValue(i))==( Math.round(solution.getVariableValue(j))))
				 {
					 int classkey=(int) Math.round(solution.getVariableValue(i));
					 if(jsomap.get(classkey)==null)//如果构建没有json数组
					 {
						 //合并两个point，形成rectangle
						 //获取i,j两个点对象
						 RectangleMBR ren= new RectangleMBR(0, 0, 0, 0);
						 Point pointtemp1=temRanbojs.randomobjects.get(i).getPoint();
						 Point pointtemp2= temRanbojs.randomobjects.get(j).getPoint();
						 ren= ren.accumulatePoints(pointtemp1,pointtemp2 );
						 jsomap.put(classkey, ren);	 
					 }
					 else{
						 RectangleMBR ren= (RectangleMBR) jsomap.get(classkey);
						 ren=ren.accumulateRect(ren,temRanbojs.getObject(j).getPoint());
						 jsomap.put(classkey, ren);
					 }
				 }
			 }
			
		 }
		 int classNumber= jsomap.size();
		 float totalCross=0;
		 float tatalsqure=0;
		 for(int i=0;i<classNumber;i++)
		 {
			 for(int j=i+1;j<classNumber;j++)
			 {
				 if((RectangleMBR)jsomap.get(i)!=null&&(RectangleMBR)jsomap.get(j)!=null)
				 {
				 totalCross=+RectangleMBR.crossSqureRect( (RectangleMBR)jsomap.get(i), (RectangleMBR) jsomap.get(j)); 
				 tatalsqure=+ RectangleMBR.totalSqureRect( (RectangleMBR)jsomap.get(i), (RectangleMBR) jsomap.get(j));
				 }
			}
		 }
		 if(totalCross==0)
		 {
			 System.out.println("oute");
		 }
		f[0]=totalCross;
		f[1]=tatalsqure;
	    solution.setObjective(0, f[0]);
	    solution.setObjective(1, f[1]);
	
		  JMetalLogger.logger.info("Total totalCross: " + totalCross);
		  JMetalLogger.logger.info("Total tatalsqure: " + tatalsqure);
	      
	}




	
	
}
