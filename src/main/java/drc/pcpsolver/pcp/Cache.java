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

import java.util.HashMap;
import drc.jsearch.*;

public class Cache implements ClosedListInterface
{
    protected final HashMap<Configuration, Integer> configurations;
    protected final int maxSize;

    public Cache (int maxSize) {
	this.configurations = new HashMap<Configuration, Integer>();
	this.maxSize = maxSize;
    }

    // Return true if, and only if, the configuration is already in
    // the cache with the same or lower matchLength.
    @Override
    public boolean contains (StateInterface state) {
	PcpState pcpState = (PcpState) state;
	Integer matchLength = configurations.get(pcpState.getConfiguration());
	return (matchLength != null) && (matchLength <= pcpState.getMatchLength());
    }

    // Add a configuration to the cache if it is not yet in it and
    // there is still room. If the same configuration is already
    // cached, but has a longer match, the old configuration is
    // replaced by the new one. Return true if, and only if, the
    // configuration is added to the cache or replaces an older one.
    @Override
    public boolean add (StateInterface state) {
	PcpState pcpState = (PcpState) state;
	Integer matchLength = configurations.get(pcpState.getConfiguration());
	if (matchLength == null) {
	    if (size() < maxSize) {
		configurations.put(pcpState.getConfiguration(), pcpState.getMatchLength());
		return true;
	    } else {
		return false;
	    }
	} else if (pcpState.getMatchLength() < matchLength) {
	    configurations.put(pcpState.getConfiguration(), pcpState.getMatchLength());
	    return true;
	} else {
	    return false;
	}
    }

    // Empty the cache.
    @Override
    public void clear () {
	configurations.clear();
    }

    // Return the number of configurations in the cache.
    @Override
    public int size () {
	return configurations.size();
    }
}
