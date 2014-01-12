/*
PCPSolver. Java solver for the Post Correspondence Problem.
Copyright 2013, 2014 David Catteeuw

This file is part of PCPSolver.

PCPSolver is free software: you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the
Free Software Foundation, either version 3 of the License, or (at your
option) any later version.

PCPSolver is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License
along with PCPSolver.  If not, see <http://www.gnu.org/licenses/>.
*/

package drc.pcpsolver.pcp;

import java.util.*;
import drc.jsearch.*;

public class Solver
{
    public static final int DEFAULT_CACHE_SIZE = 100000;
    public static final int PROBE_DEPTH = 30;
    
    public final IterativeDeepeningAStarSearch searchAlgorithm;
    public final GenericSearchAlgorithm probeAlgorithm;
    //    public final GenericSearchAlgorithm algorithm = null;
    
    public enum ReasonNoSolution
    {
	NO_PREFIX, NO_POSTFIX, LENGTH_UNBALANCED;
	
	public String explanation () {
	    switch (this) {
	    case NO_PREFIX:
		return "There is no domino to start the match.";
	    case NO_POSTFIX:
		return "There is no domino to end the match.";
	    case LENGTH_UNBALANCED:
		return "There is no domino to shrink a configuration in " +
		    "the top and/or the bottom.";
	    }
	    return null;
	}
    }
    
    public Solver () {
	// TODO: Autoscale cache size with available memory.
	searchAlgorithm = new IterativeDeepeningAStarSearch(new Cache(DEFAULT_CACHE_SIZE));
	probeAlgorithm = new GenericSearchAlgorithm(new LifoOpenList(), 
						    new Cache(DEFAULT_CACHE_SIZE),
						    new SimpleDepthLimit(PROBE_DEPTH));
    }
    
    public ReasonNoSolution findReasonNoSolution (Pcp pcp) {
	if (!pcp.hasPrefix())
	    return ReasonNoSolution.NO_PREFIX;
	
	if (!pcp.hasPostfix())
	    return ReasonNoSolution.NO_POSTFIX;
	
	if (!pcp.hasTrivialSolution() && 
	    (!pcp.hasLongerTop() || !pcp.hasLongerBottom()))
	    return ReasonNoSolution.LENGTH_UNBALANCED;
	
	return null;
    }
    
    // // TODO: How to correctly handle a solution.
    // public double avgBranchingFactor (int depth) {
    // 	double result = 0.0;
    // 	int i = 1;
    // 	while (i < depth && searchAlgorithm.nofNodesVisited(i) > 0) {
    // 	    result += searchAlgorithm.nofNodesVisited(i) 
    // 		/ searchAlgorithm.nofNodesVisited(i-1);
    // 	    i++;
    // 	}
    // 	result /= depth - 1;
    // 	return result;
    // }
    
    // public double branchingFactor (Pcp pcp, int depth) {
    // 	searchAlgorithm.cancel(false);
    // 	searchAlgorithm.resetStatistics();
    // 	searchAlgorithm.setMaxDepth(depth);
    // 	Node node = searchAlgorithm.execute(pcp);
    // 	return avgBranchingFactor(depth);
    // }
    
    public Node search (Pcp pcp, int maxDepth) {
    	searchAlgorithm.cancel(false);
    	searchAlgorithm.setMaxDepth(maxDepth);
    	return searchAlgorithm.execute(pcp);
    }
    
    public List<Domino> nodeToMatch (Node node, boolean reverse) {
	if (reverse) {
	    LinkedList<Domino> reversedMatch = new LinkedList<Domino>();
	    for (ActionInterface action : node.actionsTo()) {
		Domino domino = (Domino) action;		
		reversedMatch.addFirst(domino.reverse());
	    }
	    return reversedMatch;
	} else {
	    List<ActionInterface> actions = node.actionsTo();
	    ArrayList<Domino> match = new ArrayList<Domino>(actions.size());
	    for (ActionInterface action : actions) {
		match.add((Domino) action);
	    }
	    return match;
	}
    }
    
    public List<Domino> findMatch (Pcp pcp, int maxDepth) {
	System.out.println("PCP: " + pcp);
	
	// forward probe
	probeAlgorithm.cancel(false);
	Node node = probeAlgorithm.execute(pcp);
	if (node != null && pcp.isSolution(node.state)) {
	    return nodeToMatch(node, false);
	}
	double forwardNofNodesVisited = probeAlgorithm.nofNodesVisited();
	System.out.println("forward: " + forwardNofNodesVisited);
	
	// backward probe
	Pcp reversePcp = pcp.reverse();
	probeAlgorithm.cancel(false);
	node = probeAlgorithm.execute(reversePcp);
	if (node != null && reversePcp.isSolution(node.state)) {
	    return nodeToMatch(node, true);
	}
	double backwardNofNodesVisited = probeAlgorithm.nofNodesVisited();
	System.out.println("backward: " + backwardNofNodesVisited);
	
	// Solve in direction with smallest number of visited nodes.
	boolean reverse = forwardNofNodesVisited > backwardNofNodesVisited;
	node = search(reverse ? reversePcp : pcp, maxDepth);
	return (node != null) ? nodeToMatch(node, reverse) : null;
    }
    
    public void cancel (boolean cancel) {
	searchAlgorithm.cancel(cancel);
    }
}
