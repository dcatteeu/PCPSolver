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

public class Domino implements ActionInterface
{
    public final String top, bottom;
    
    public Domino (String top, String bottom) {
	assert !top.isEmpty();
	assert !bottom.isEmpty();
	this.top = top;
	this.bottom = bottom;
    }

    // Return true if, and only if, the top string is a prefix of the
    // bottom string or vice versa.
    boolean canStartWith () {
	return top.startsWith(bottom) || bottom.startsWith(top);
    }

    // Return true if, and only if, the top string is a postfix of the
    // bottom string or vice versa.
    boolean canEndWith () {
	return bottom.endsWith(top) || top.endsWith(bottom);
    }

    // Return true if, and only if, this domino is a solution by
    // itself.
    boolean isSolution () {
	return top.equals(bottom);
    }

    // Return the difference in length between top and bottom string.
    int lengthDifference () {
	return top.length() - bottom.length();
    }

    // Return the reverse of this domino.
    Domino reverse () {
	return new Domino(new StringBuilder(top).reverse().toString(),
			  new StringBuilder(bottom).reverse().toString());
    }

    @Override
    public boolean equals (Object obj) {
	if (obj instanceof Domino) {
	    Domino other = (Domino) obj;
	    return top.equals(other.top) && bottom.equals(other.bottom);
	} else {
	    return false;
	}
    }

    @Override
    public String toString () {
	return new String("<Domino: " + top + "/" + bottom + ">");
    }
}
