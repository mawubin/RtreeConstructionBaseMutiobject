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
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.SimpleRandomMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT1;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import com.github.davidmoten.rtree.geometry.Geometries;

import Rtree.RandomObjects;

public class Solution {

	public static void main(String[] args) throws FileNotFoundException {	 
	        Problem<IntegerSolution> problem;//定义 问题
	        Algorithm<List<IntegerSolution>> algorithm;//
	        CrossoverOperator<IntegerSolution> crossover;
	        MutationOperator<IntegerSolution> mutation;
	        SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;
	        String referenceParetoFront = "";
	      
	       //配置数据种子
	        RandomObjects ranObjects=new RandomObjects(Const.numberOfSpatialObjects,Geometries.point(0,0),Geometries.point(100,100));
	        //定义优化问题
	       // problem = new ZDT1();
	        problem= new segProblem(Const.numberOfSpatialObjects,ranObjects);
	        //配置SBX交叉算子
	        double crossoverProbability = 0.9;
	        double crossoverDistributionIndex = 20.0;
//	        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
//	        
	        crossover=  new  IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex);
//	        
	        //配置变异算子
	        double mutationProbability = 1.0 / problem.getNumberOfVariables();
	        //  double mutationDistributionIndex = 20.0 ;
	        mutation = new IntegerPolynomialMutation();
	        //配置选择算子
	        selection = new BinaryTournamentSelection<IntegerSolution>(
	                new RankingAndCrowdingDistanceComparator<IntegerSolution>());
	        //将组件注册到algorithm
	        algorithm = new NSGAIIBuilder<IntegerSolution>(problem, crossover, mutation)
	                .setSelectionOperator(selection)
	                .setMaxEvaluations(Const.MaxEvaluations)
	                .setPopulationSize(Const.PopulationSize)
	                .build();
	/*       或者用这样的方法注册一个算法
	          evaluator = new SequentialSolutionListEvaluator<DoubleSolution>();
	          algorithm = new NSGAII<DoubleSolution>(problem, 25000, 100, crossover,
	          mutation, selection, evaluator);
	*/
	        //用AlgorithmRunner运行算法
	        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
	        //获取结果集
	        List<IntegerSolution> population = algorithm.getResult();
	        long computingTime = algorithmRunner.getComputingTime();
	        
	        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
	        //将全部种群打印到文件中
	        printFinalSolutionSet(population);
	        if (!referenceParetoFront.equals("")) printQualityIndicators(population, referenceParetoFront);
	    }
}
