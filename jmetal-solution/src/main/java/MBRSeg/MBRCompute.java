package MBRSeg;

import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;



public class MBRCompute {
	@SuppressWarnings("serial")
	IntegerSolution integersolution = new IntegerSolution() {
		
		public void setVariableValue(int index, Integer value) {
			// TODO Auto-generated method stub
			
		}
		
		public void setObjective(int index, double value) {
			// TODO Auto-generated method stub
			
		}
		
		public void setAttribute(Object id, Object value) {
			// TODO Auto-generated method stub
			
		}
		
		public String getVariableValueString(int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public Integer getVariableValue(int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public double getObjective(int index) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public int getNumberOfVariables() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public int getNumberOfObjectives() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public Object getAttribute(Object id) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public Solution<Integer> copy() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public Integer getUpperBound(int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public Integer getLowerBound(int index) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	public MBRCompute(IntegerSolution integersolution){
		this.integersolution=integersolution;
	}
	public double MBRComputeCrossSqure(){
		
		int numberOfVariables=integersolution.getNumberOfVariables();
		//Geometries.point(, y);
		
		
		return 0;
	}
	public double MBRComputeTotalSqure(){
		
		
		return 0;
	}
	public double MBRComputeTextSimilar(){
		
		
		return 0;
	}
	
}
