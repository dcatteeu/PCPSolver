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

public class Pcp extends AbstractSearchProblem
{
    final Domino dominoes[];

    // The number of symbols the top (bottom) string can grow more
    // than the bottom (top) string. Useful for heuristic.
    final int maxTopGrow, maxBottomGrow;

    Domino[] withoutDoubles (Domino dominoes[]) {
	LinkedList<Domino> list = new LinkedList<Domino>();
	for (Domino domino : dominoes) {
	    if (!list.contains(domino)) {
		list.addFirst(domino);
	    }
	}
	return list.toArray(new Domino[list.size()]);
    }

    public Pcp (Domino dominoes[]) {	
	this.dominoes = withoutDoubles(dominoes);
	int maximum = Integer.MIN_VALUE, minimum = Integer.MAX_VALUE;
	for (Domino domino : this.dominoes) {
	    maximum = Math.max(maximum, domino.lengthDifference());
	    minimum = Math.min(minimum, domino.lengthDifference());
	}
	maxTopGrow = maximum;
	maxBottomGrow = -minimum;
    }

    // Return the reverse PCP instance. It has all strings reversed.
    public Pcp reverse () {
	Domino reverseDominoes[] = new Domino[dominoes.length];
	for (int i = 0; i < dominoes.length; i++) {
	    reverseDominoes[i] = dominoes[i].reverse();
	}
	return new Pcp(reverseDominoes);
    }

    // Return true if, and only if, there is a domino with which the
    // match can start.
    public boolean hasPrefix () {
	for (Domino domino : dominoes) {
	    if (domino.canStartWith()) return true;
	}
	return false;
    }

    // Return true if, and only if, there is a domino with which the
    // match can end.
    public boolean hasPostfix () {
	for (Domino domino : dominoes) {
	    if (domino.canEndWith()) return true;
	}
	return false;
    }

    // Return true if, and only if, there is a domino that is a
    // solution by itself.
    public boolean hasTrivialSolution () {
	for (Domino domino : dominoes) {
	    if (domino.isSolution()) return true;
	}
	return false;
    }

    // Return true if, and only if, there is a domino which has a
    // longer top than bottom string. Such a domino can shrink the
    // configuration if it is in the bottom.
    public boolean hasLongerTop () {
	for (Domino domino : dominoes) {
	    if (domino.lengthDifference() > 0) return true;
	}
	return false;
    }

    // Return true if, and only if, there is a domino which has a
    // longer bottom than top string. Such a domino can shrink the
    // configuration if it is in the top.
    public boolean hasLongerBottom () {
	for (Domino domino : dominoes) {
	    if (domino.lengthDifference() < 0) return true;
	}
	return false;
    }

    @Override
    public StateInterface getInitialState () {
	return new PcpState("", true, 0);
    }

    @Override
    public boolean isSolution (StateInterface state) {
	PcpState pcpState = (PcpState) state;
	return pcpState.getConfiguration().getValue().isEmpty()
	    && pcpState.getMatchLength() > 0;
    }

    @Override
    public List<StateActionPair> successors (StateInterface state) {
	PcpState from = (PcpState) state;
	List<StateActionPair> stateActionPairs
	    = new LinkedList<StateActionPair>();
	PcpState to;
	for (Domino domino : dominoes) {
	    to = from.add(domino);
	    if (to != null) {
		stateActionPairs.add(new StateActionPair(to, domino));
	    }
	}
	return stateActionPairs;
    }

    @Override
    public double heuristic (StateInterface state) {
	PcpState pcpstate = (PcpState) state;
	double h = 1.0 * pcpstate.lengthDifference();
	if (h < 0) {
	    h = Math.ceil(-h / maxTopGrow);
	} else if (h > 0) {
	    h = Math.ceil(h / maxBottomGrow);
	} else {
	    h = 0;
	}
	assert h >= 0;
	return h;
    }

    @Override
    public String toString () {
	if (dominoes.length <= 0) {
	    return new String("<Pcp: no dominoes>");
	}
	String result = new String("<Pcp: " + dominoes[0]);
	for (int i = 1; i < dominoes.length; i++) {
	    result = result.concat(", " + dominoes[i]);
	}
	return result.concat(">");
    }
}
