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
import java.awt.event.*;
import javax.swing.*;

import drc.pcpsolver.pcp.*;

public class Main
{    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("PCP Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	JComponent contentPane = (JComponent) frame.getContentPane();
	contentPane.setLayout(new BorderLayout(5, 5));
	contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	InstancePanel instancePanel = new InstancePanel();
	instancePanel.setPreferredSize(new Dimension(400, 200));
        contentPane.add(instancePanel, BorderLayout.CENTER);
	
	SolvePanel solvePanel = new SolvePanel(instancePanel);
	solvePanel.setPreferredSize(new Dimension(400, 200));
	contentPane.add(solvePanel, BorderLayout.PAGE_END);
	
	frame.setResizable(false);
	frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
