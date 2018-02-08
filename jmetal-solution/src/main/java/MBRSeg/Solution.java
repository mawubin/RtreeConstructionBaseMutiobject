package MBRSeg;

import static org.uma.jmetal.runner.AbstractAlgorithmRunner.printFinalSolutionSet;
import static org.uma.jmetal.runner.AbstractAlgorithmRunner.printQualityIndicators;

import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import com.github.davidmoten.rtree.geometry.Geometries;

import Chart.Paint;
import Rtree.RandomObjects;

public class Solution {

	public static void main(String[] args) throws FileNotFoundException {	 
	        Problem<IntegerSolution> problem;//瀹氫箟 闂
	        Algorithm<List<IntegerSolution>> algorithm;//
	        CrossoverOperator<IntegerSolution> crossover;
	        MutationOperator<IntegerSolution> mutation;
	        SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;
	        String referenceParetoFront = "";
	      //閰嶇疆鏁版嵁绉嶅瓙
	        RandomObjects ranObjects=new RandomObjects(Const.numberOfSpatialObjects,Geometries.point(0,0),Geometries.point(1000,1000));
	        //瀹氫箟浼樺寲闂
	        segProblem seg=new segProblem(Const.numberOfSpatialObjects, ranObjects);       	
	       // problem = new ZDT1();
	        problem= seg;
	       
	        //閰嶇疆SBX浜ゅ弶绠楀瓙
	        double crossoverProbability = 0.9;
	        double crossoverDistributionIndex = 20.0;
//	        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
//	        
	        crossover=  new  IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex);
//	        
	        //閰嶇疆鍙樺紓绠楀瓙
	        double mutationProbability = 1.0 / problem.getNumberOfVariables();
	        //  double mutationDistributionIndex = 20.0 ;
	        mutation = new IntegerPolynomialMutation();
	        //閰嶇疆閫夋嫨绠楀瓙
	        selection = new BinaryTournamentSelection<IntegerSolution>(
	                new RankingAndCrowdingDistanceComparator<IntegerSolution>());
	        //灏嗙粍浠舵敞鍐屽埌algorithm
	        algorithm = new NSGAIIBuilder<IntegerSolution>(problem, crossover, mutation)
	                .setSelectionOperator(selection)
	                .setMaxEvaluations(Const.MaxEvaluations)
	                .setPopulationSize(Const.PopulationSize)
	                .build();
	/*       鎴栬�呯敤杩欐牱鐨勬柟娉曟敞鍐屼竴涓畻娉�
	          evaluator = new SequentialSolutionListEvaluator<DoubleSolution>();
	          algorithm = new NSGAII<DoubleSolution>(problem, 25000, 100, crossover,
	          mutation, selection, evaluator);
	*/
	        //鐢ˋlgorithmRunner杩愯绠楁硶
	        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
	        //鑾峰彇缁撴灉闆�
	        List<IntegerSolution> population = algorithm.getResult();
	        System.out.println(seg.count);
	        long computingTime = algorithmRunner.getComputingTime();
	        seg.paint();
	        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
	        //灏嗗叏閮ㄧ缇ゆ墦鍗板埌鏂囦欢涓�
	        printFinalSolutionSet(population);
	        Paint.paintResult(population, ranObjects);
	        if (!referenceParetoFront.equals("")) printQualityIndicators(population, referenceParetoFront);
	    }
}
