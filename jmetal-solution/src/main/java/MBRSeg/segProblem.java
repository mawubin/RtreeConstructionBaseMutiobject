package MBRSeg;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import Rtree.RandomObjects;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class segProblem extends AbstractDoubleProblem{

	public RandomObjects ranbojs;
	public segProblem(int numberOfVariables,RandomObjects ranobjs) {
		setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(4);
	    setName("segProblem");
	    this.ranbojs=ranobjs;
	    List<Integer> lowerLimit = new ArrayList<Integer>(getNumberOfVariables()) ;
	    List<Integer> upperLimit = new ArrayList<Integer>(getNumberOfVariables()) ;
		
	    for (int i = 0; i < getNumberOfVariables(); i++) {
	        lowerLimit.add(0);
	        upperLimit.add(Const.numberOfClass);
	      }
	}



	public void evaluate(DoubleSolution solution) {
		// TODO Auto-generated method stub
		 double[] f = new double[getNumberOfObjectives()];
		 int numberofobjects=getNumberOfObjectives();
		 RandomObjects temRanbojs= new RandomObjects(numberofobjects);
		 temRanbojs=this.ranbojs;
		 JSONArray jsonarray= new JSONArray();
		 JSONObject jso=new JSONObject();
		 for(int i=0;i>numberofobjects;i++)
		 {
			 for(int j=i;j<numberofobjects;j++)
			 {
				 if( solution.getVariableValue(i)== solution.getVariableValue(j))
				 {
					 
				 }
			 }
			
		 }
		
	}
	
	
}
