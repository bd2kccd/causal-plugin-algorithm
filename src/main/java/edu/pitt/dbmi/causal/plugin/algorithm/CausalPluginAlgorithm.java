package edu.pitt.dbmi.causal.plugin.algorithm;

import java.io.PrintStream;
import java.util.List;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginException;
import org.pf4j.PluginWrapper;

import edu.cmu.tetrad.algcomparison.algorithm.Algorithm;
import edu.cmu.tetrad.algcomparison.score.ScoreWrapper;
import edu.cmu.tetrad.algcomparison.utils.HasKnowledge;
import edu.cmu.tetrad.algcomparison.utils.TakesInitialGraph;
import edu.cmu.tetrad.algcomparison.utils.UsesScoreWrapper;
import edu.cmu.tetrad.annotation.AlgType;
import edu.cmu.tetrad.data.DataModel;
import edu.cmu.tetrad.data.DataType;
import edu.cmu.tetrad.data.IKnowledge;
import edu.cmu.tetrad.data.Knowledge2;
import edu.cmu.tetrad.graph.EdgeListGraph;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.search.SearchGraphUtils;
import edu.cmu.tetrad.util.Parameters;
import edu.pitt.dbmi.tetrad.plugin.api.CausalPlugin;

/**
 * @author Chirayu Wongchokprasitti, PhD
 *
 *         Sep 21, 2018 3:22:00 PM
 */
public class CausalPluginAlgorithm extends Plugin {

	public CausalPluginAlgorithm(PluginWrapper wrapper) {
		super(wrapper);
	}

	@Override
	public void start() throws PluginException {
		System.out.println("CausalPluginAlgorithm.start()");
	}

	@Override
	public void stop() throws PluginException {
		System.out.println("CausalPluginAlgorithm.stop()");
		super.stop();
	}

	@Extension
	@edu.cmu.tetrad.annotation.Algorithm(
	        name = "Plugin-FGES",
	        command = "plugin-fges",
	        algoType = AlgType.forbid_latent_common_causes
	)
	public static class AlgorithmExtension
			implements CausalPlugin, Algorithm, TakesInitialGraph, HasKnowledge, UsesScoreWrapper {

		private static final long serialVersionUID = 1L;

		private boolean compareToTrue = false;
		private ScoreWrapper score;
		private Algorithm algorithm = null;
		private Graph initialGraph = null;
		private IKnowledge knowledge = new Knowledge2();

		@Override
		public Graph search(DataModel dataSet, Parameters parameters) {
			if (algorithm != null) {
				initialGraph = algorithm.search(dataSet, parameters);
			}

			edu.cmu.tetrad.search.Fges search = new edu.cmu.tetrad.search.Fges(score.getScore(dataSet, parameters));
			search.setFaithfulnessAssumed(parameters.getBoolean("faithfulnessAssumed"));
			search.setKnowledge(knowledge);
			search.setVerbose(parameters.getBoolean("verbose"));
			search.setMaxDegree(parameters.getInt("maxDegree"));
			search.setSymmetricFirstStep(parameters.getBoolean("symmetricFirstStep"));

			Object obj = parameters.get("printStream");
			if (obj instanceof PrintStream) {
				search.setOut((PrintStream) obj);
			}

			if (initialGraph != null) {
				search.setInitialGraph(initialGraph);
			}

			return search.search();
		}

		@Override
		public Graph getComparisonGraph(Graph graph) {
			if (compareToTrue) {
				return new EdgeListGraph(graph);
			} else {
				return SearchGraphUtils.patternForDag(new EdgeListGraph(graph));
			}
		}

		@Override
		public String getDescription() {
			return "Causal Plugin Search Algorithm...";
		}

		@Override
		public DataType getDataType() {
			return score.getDataType();
		}

		@Override
		public List<String> getParameters() {
			List<String> parameters = score.getParameters();
			parameters.add("faithfulnessAssumed");
			parameters.add("symmetricFirstStep");
			parameters.add("maxDegree");
			parameters.add("verbose");
			return parameters;
		}

		@Override
		public Graph getInitialGraph() {
			return initialGraph;
		}

		@Override
		public void setInitialGraph(Graph initialGraph) {
			this.initialGraph = initialGraph;
		}

		@Override
		public void setInitialGraph(Algorithm algorithm) {
			this.algorithm = algorithm;
		}

		@Override
		public IKnowledge getKnowledge() {
			return knowledge;
		}

		@Override
		public void setKnowledge(IKnowledge knowledge) {
			this.knowledge = knowledge;
		}

		@Override
		public void setScoreWrapper(ScoreWrapper score) {
			this.score = score;
		}

		public void setCompareToTrue(boolean compareToTrue) {
			this.compareToTrue = compareToTrue;
		}

		@Override
		public String getAlgorithmDescriptions() {
			return "This is an algorithm description.";
		}

	}

}
