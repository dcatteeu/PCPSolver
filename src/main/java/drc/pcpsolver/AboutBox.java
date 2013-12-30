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

package drc.pcpsolver;

import java.awt.*;
import javax.swing.*;

public class AboutBox extends JDialog
{
    public AboutBox(JFrame parent, String version) {
	super(parent, "About", true);

	JPanel pane = new JPanel();	
	pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
	pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	pane.add(new JLabel("<html><h3>PCP Solver</h3></html>"));
	pane.add(new JLabel("Version " + version));
	pane.add(Box.createRigidArea(new Dimension(0, 20)));
	pane.add(new JLabel("Java solver for Post's Correspondence Problem"));
	pane.add(new JLabel("http://dcatteeu.github.io/pcpsolver.html"));
	pane.add(Box.createRigidArea(new Dimension(0, 20)));
	pane.add(new JLabel("Copyright (C) 2013 David Catteeuw"));
	pane.add(new JLabel("PCP Solver is licensed under GPLv3."));
	
	setContentPane(pane);
	setResizable(false);
	pack();
    }
}
