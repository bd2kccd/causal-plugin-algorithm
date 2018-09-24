/**
 * 
 */
package edu.pitt.dbmi.causal.plugin.algorithm;

import java.util.List;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginException;
import org.pf4j.PluginWrapper;

import edu.cmu.tetrad.data.DataModel;
import edu.cmu.tetrad.data.DataType;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.util.Parameters;
import edu.pitt.dbmi.tetrad.plugin.api.PluginAlgorithm;

/**
 * @author Chirayu Wongchokprasitti, PhD
 *
 * Sep 21, 2018 3:22:00 PM 
 */
public class CausalPluginAlgorithm extends Plugin {

	public CausalPluginAlgorithm(PluginWrapper wrapper) {
		super(wrapper);
	}
	
	@Override
	public void start() throws PluginException {
		
	}

	@Override
	public void stop() throws PluginException {
		
	}

	@Extension
	public static class AlgorithmExtension implements PluginAlgorithm {

		private static final long serialVersionUID = 1L;

		public Graph search(DataModel dataSet, Parameters parameters) {
			return null;
		}

		public Graph getComparisonGraph(Graph graph) {
			return null;
		}

		public String getDescription() {
			return null;
		}

		public DataType getDataType() {
			return null;
		}

		public List<String> getParameters() {
			return null;
		}
		
	}

}
