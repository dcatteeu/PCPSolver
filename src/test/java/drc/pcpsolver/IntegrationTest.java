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

package drc.pcpsolver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import drc.pcpsolver.pcp.*;

@RunWith(JUnit4.class)
public class IntegrationTest
{
    final Solver solver = new Solver();

    final Pcp[] pcps 
	= { new Pcp(new Domino[]{ new Domino("10","101"),
				  new Domino("011","11"),
				  new Domino("101","011") }), // no, repetition
	    new Pcp(new Domino[]{ new Domino("100","1"),
				  new Domino("0","100"),
				  new Domino("1","00") }), // yes
	    // new Pcp(new Domino[]{ new Domino("100","1"),
	    // 			 new Domino("0","100"),
	    // 			 new Domino("1","0") }), // yes, 8s
	    new Pcp(new Domino[]{ new Domino("01","1"),
				  new Domino("1","011"),
				  new Domino("011","0") }), // yes
	    new Pcp(new Domino[]{ new Domino("0","1"),
				  new Domino("01","0"),
				  new Domino("1","101") }), // yes
	    new Pcp(new Domino[]{ new Domino("1","111"),
				  new Domino("10111","10"),
				  new Domino("10","0") }), // yes
	    new Pcp(new Domino[]{ new Domino("01","0"),
				  new Domino("110010","0"),
				  new Domino("1","1111"),
				  new Domino("11","01") }), // yes
	    new Pcp(new Domino[]{ new Domino("0", "10"),
				  new Domino("01", "1")}),  //NO_PREFIX
	    new Pcp(new Domino[]{ new Domino("11", "101"),
				  new Domino("011","11"),
				  new Domino("101","011")}),  //NO_PREFIX
	    new Pcp(new Domino[]{ new Domino("10","101"),
				  new Domino("10","11"),
				  new Domino("01","011")}), //NO_POSTFIX,
	    new Pcp(new Domino[]{ new Domino("10","101"),
				  new Domino("01","101"),
				  new Domino("10","11")}) }; //UNBALANCED,
    final Solver.ReasonNoSolution[] reasons 
	= { null, null, /*null,*/ null, null, null, null,
	    Solver.ReasonNoSolution.NO_PREFIX,
	    Solver.ReasonNoSolution.NO_PREFIX,
	    Solver.ReasonNoSolution.NO_POSTFIX,
	    Solver.ReasonNoSolution.LENGTH_UNBALANCED };
    final boolean[] results = { false, true, /*true,*/ true, true, true, true,
				false, false, false, false };
    
    @Test
    public void testFilters () {
	for (int i = 0; i < reasons.length; i++) {
	    assertEquals(reasons[i], solver.findReasonNoSolution(pcps[i]));
	}
    }
    
    @Test
    public void testSearch() {
	for (int i = 0; i < results.length; i++) {
	    assertEquals(results[i], solver.findMatch(pcps[i], 75) != null);
	}
    }
}
