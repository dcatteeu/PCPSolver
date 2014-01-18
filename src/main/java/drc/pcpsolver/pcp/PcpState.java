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

import drc.jsearch.*;

public class PcpState implements StateInterface
{
    protected final Configuration configuration;
    protected final int matchLength;
    
    public PcpState (String configuration, boolean inTop, int matchLength) {
	this.configuration = new Configuration(configuration, inTop);
	this.matchLength = matchLength;
    }
    
    // Return the configuration that results from adding the given
    // domino to the current configuration.
    PcpState add (Domino domino) {
	String top, bottom;
	if (configuration.isInTop()) {
	    top = configuration.getValue().concat(domino.top);
	    bottom = domino.bottom;
	} else {
	    top = domino.top;
	    bottom = configuration.getValue().concat(domino.bottom);	    
	}
	int matchLength = 0;
	while (matchLength < top.length() && matchLength < bottom.length()
	       && top.charAt(matchLength) == bottom.charAt(matchLength)) {
	    matchLength++;
	}
	if (matchLength == top.length()) {
	    return new PcpState(bottom.substring(matchLength), false,
				this.matchLength + matchLength);
	} else if (matchLength == bottom.length()) {
	    return new PcpState(top.substring(matchLength), true,
				this.matchLength + matchLength);
	} else {
	    return null;
	}
    }

    public Configuration getConfiguration () {
	return configuration;
    }
    
    public int getMatchLength () {
	return matchLength;
    }
    
    public int lengthDifference () {
	return (configuration.isInTop() ? +1 : -1) * configuration.length();	
    }
    
    // // Return true if, and only if, the two configurations are equal:
    // // their top and bottom string must match and they must have at
    // // least matchLength 1; or they must both be the initial configuration.
    // @Override
    // public boolean equals (Object other) {
    // 	if (other instanceof PcpState) {
    // 	    PcpState otherPcp = (PcpState) other;
    // 	    if (matchLength == 0) {
    // 		// The initial configuration.
    // 		return otherPcp.matchLength == 0;
    // 	    } else {
    // 		return matchLength > 0 && otherPcp.matchLength > 0
    // 		    && top.equals(otherPcp.top)
    // 		    && bottom.equals(otherPcp.bottom);
    // 	    }
    // 	} else {
    // 	    return false;
    // 	}
    // }

    // @Override
    // public int hashCode () {
    // 	return hashcode;
    // }

    @Override
    public String toString () {
	return new String("<PcpState: " + configuration + ", " + matchLength + ">");
    }
}
