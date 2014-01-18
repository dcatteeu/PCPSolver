/*
PCPSolver. Java solver for the Post Correspondence Problem.
Copyright 2014 David Catteeuw

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

public class Configuration
{
    protected final String value;
    protected final boolean inTop;
    protected final int hashCode;

    public Configuration (String value, boolean inTop) {
	this.value = value;
	this.inTop = inTop;
	this.hashCode = (inTop ? 0 : 1) | (value.hashCode() << 1);
    }

    public int length () {
	return value.length();
    }

    public String getValue () {
	return value;
    }

    public boolean isInTop () {
	return inTop;
    }

    // Return true if, and only if, the two configurations are equal.
    @Override
    public boolean equals (Object o) {
	if (o instanceof Configuration) {
	    Configuration other = (Configuration) o;
	    return (this.inTop == other.inTop) && this.value.equals(other.value);
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode () {
	return hashCode;
    }

    @Override
    public String toString () {
	return new String("<Configuration: " + value + " in " +
			  (inTop ? "top" : "bottom") + ">");
    }
}
