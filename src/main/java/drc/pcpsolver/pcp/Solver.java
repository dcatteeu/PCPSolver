/*
PCPSolver. Java solver for the Post Correspondence Problem.
Copyright 2013 David Catteeuw

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

import drc.jsearch.*;

public class Solver
{
    public static final int DEFAULT_CACHE_SIZE = 100000;
    public static final int PROBE_DEPTH = 75;

    public final IterativeDeepeningAStarSearch searchAlgorithm;

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

    public Node findMatch (Pcp pcp, double maxDepth) {
	searchAlgorithm.cancel(false);
	searchAlgorithm.resetStatistics();
	searchAlgorithm.trackNofNodesVisitedPerDepth(PROBE_DEPTH);
	searchAlgorithm.setMaxDepth(maxDepth);
	return searchAlgorithm.execute(pcp);
    }

    public void cancel (boolean cancel) {
	searchAlgorithm.cancel(cancel);
    }
}
