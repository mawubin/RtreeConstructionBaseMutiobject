package jmetelmain;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.SimpleRandomMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT1;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import java.io.FileNotFoundException;
import java.util.List;

import static org.uma.jmetal.runner.AbstractAlgorithmRunner.printFinalSolutionSet;
import static org.uma.jmetal.runner.AbstractAlgorithmRunner.printQualityIndicators;

public class ZDT1_jMetal {
	 public static void main(String[] args) throws FileNotFoundException {
		 
		 
	        Problem<DoubleSolution> problem;//瀹氫箟 闂
	        Algorithm<List<DoubleSolution>> algorithm;
	        CrossoverOperator<DoubleSolution> crossover;
	        MutationOperator<DoubleSolution> mutation;
	        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
	        String referenceParetoFront = "";
	        //瀹氫箟浼樺寲闂
	        zdttest zdtt= new zdttest();
	        problem =zdtt;
	       
	        //閰嶇疆SBX浜ゅ弶绠楀瓙
	        double crossoverProbability = 0.9;
	        double crossoverDistributionIndex = 20.0;
	        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
	        //閰嶇疆鍙樺紓绠楀瓙
	        double mutationProbability = 1.0 / problem.getNumberOfVariables();
	        //  double mutationDistributionIndex = 20.0 ;
	        mutation = new SimpleRandomMutation(mutationProbability);
	        //閰嶇疆閫夋嫨绠楀瓙
	        selection = new BinaryTournamentSelection<DoubleSolution>(
	                new RankingAndCrowdingDistanceComparator<DoubleSolution>());
	        //灏嗙粍浠舵敞鍐屽埌algorithm
	        algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation)
	                .setSelectionOperator(selection)
	                .setMaxEvaluations(25000)
	                .setPopulationSize(100)
	                .build();
	/*       鎴栬�呯敤杩欐牱鐨勬柟娉曟敞鍐屼竴涓畻娉�
	          evaluator = new SequentialSolutionListEvaluator<DoubleSolution>();
	          algorithm = new NSGAII<DoubleSolution>(problem, 25000, 100, crossover,
	          mutation, selection, evaluator);
	*/
	        //鐢ˋlgorithmRunner杩愯绠楁硶
	        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
	        //鑾峰彇缁撴灉闆�
	        List<DoubleSolution> population = algorithm.getResult();
	        long computingTime = algorithmRunner.getComputingTime();
	        zdtt.paint();
	        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
	        //灏嗗叏閮ㄧ缇ゆ墦鍗板埌鏂囦欢涓�
	        printFinalSolutionSet(population);
	        if (!referenceParetoFront.equals("")) printQualityIndicators(population, referenceParetoFront);
	    }
}
