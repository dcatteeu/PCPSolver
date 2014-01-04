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

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import drc.pcpsolver.pcp.*;

@RunWith(JUnit4.class)
public class DominoTest
{
    final Domino d1 = new Domino("abaa", "baa");
    final Domino d2 = new Domino("aaba", "aab");
    final Domino d3 = new Domino("aba", "baa");
    final Domino d4 = new Domino("aba", "aba");
    
    @Test
    public void testCanStartWith () {
	assertFalse(d1.canStartWith());
	assertTrue(d2.canStartWith());
	assertFalse(d3.canStartWith());
	assertTrue(d4.canStartWith());
    }
    
    @Test
    public void testCanEndWith () {
	assertTrue(d1.canEndWith());
	assertFalse(d2.canEndWith());
	assertFalse(d3.canEndWith());
	assertTrue(d4.canEndWith());
    }
    
    @Test
    public void testIsSolution () {
	assertFalse(d1.isSolution());
	assertFalse(d2.isSolution());
	assertFalse(d3.isSolution());
	assertTrue(d4.isSolution());
    }

    @Test
    public void testLengthDifference () {
	assertEquals(1, d1.lengthDifference());
	assertEquals(1, d2.lengthDifference());
	assertEquals(0, d3.lengthDifference());
	assertEquals(0, d4.lengthDifference());	
    }

    @Test
    public void testReverse () {
	assertEquals(d2, d1.reverse());
	assertEquals(d1, d2.reverse());
    }

    @Test
    public void testEquals () {
	assertTrue(d1.equals(d2.reverse()));
	assertFalse(d3.equals(new Domino("bb", "b")));
	assertTrue(d4.equals(new Domino("aba", "aba")));
    }
}
