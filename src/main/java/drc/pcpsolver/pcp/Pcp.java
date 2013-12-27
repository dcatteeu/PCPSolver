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

import java.util.*;
import drc.jsearch.*;

public class Pcp extends AbstractSearchProblem
{
    final Domino dominos[];

    // The number of symbols the top (bottom) string can grow more
    // than the bottom (top) string. Useful for heuristic.
    final int maxTopGrow, maxBottomGrow;

    public Pcp (Domino dominos[]) {
	this.dominos = dominos;
	int maximum = Integer.MIN_VALUE, minimum = Integer.MAX_VALUE;
	for (Domino domino : dominos) {
	    maximum = Math.max(maximum, domino.lengthDifference());
	    minimum = Math.min(minimum, domino.lengthDifference());
	}
	maxTopGrow = maximum;
	maxBottomGrow = -minimum;
    }

    // Return the reverse PCP instance. It has all strings reversed.
    public Pcp reverse () {
	Domino reverseDominos[] = new Domino[dominos.length];
	for (int i = 0; i < dominos.length; i++) {
	    reverseDominos[i] = dominos[i].reverse();
	}
	return new Pcp(reverseDominos);
    }

    // Return true if, and only if, there is a domino with which the
    // match can start.
    public boolean hasPrefix () {
	for (Domino domino : dominos) {
	    if (domino.canStartWith()) return true;
	}
	return false;
    }

    // Return true if, and only if, there is a domino with which the
    // match can end.
    public boolean hasPostfix () {
	for (Domino domino : dominos) {
	    if (domino.canEndWith()) return true;
	}
	return false;
    }

    // Return true if, and only if, there is a domino that is a
    // solution by itself.
    public boolean hasTrivialSolution () {
	for (Domino domino : dominos) {
	    if (domino.isSolution()) return true;
	}
	return false;
    }

    // Return true if, and only if, there is a domino which has a
    // longer top than bottom string. Such a domino can shrink the
    // configuration if it is in the bottom.
    public boolean hasLongerTop () {
	for (Domino domino : dominos) {
	    if (domino.lengthDifference() > 0) return true;
	}
	return false;
    }

    // Return true if, and only if, there is a domino which has a
    // longer bottom than top string. Such a domino can shrink the
    // configuration if it is in the top.
    public boolean hasLongerBottom () {
	for (Domino domino : dominos) {
	    if (domino.lengthDifference() < 0) return true;
	}
	return false;
    }

    @Override
    public StateInterface getInitialState () {
	return new PcpState("", "", 0);
    }

    @Override
    public boolean isSolution (StateInterface state) {
	PcpState pcpState = (PcpState) state;
	return pcpState.top.isEmpty() && pcpState.bottom.isEmpty()
	    && pcpState.depth > 0;
    }

    @Override
    public List<StateActionPair> successors (StateInterface state) {
	PcpState from = (PcpState) state;
	List<StateActionPair> stateActionPairs
	    = new ArrayList<StateActionPair>();
	PcpState to;
	PcpAction action;
	for (Domino domino : dominos) {
	    to = from.add(domino);
	    if (to != null) {
		action = new PcpAction(domino);
		stateActionPairs.add(new StateActionPair(to, action));
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
}
