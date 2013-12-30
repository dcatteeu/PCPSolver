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
    final static String VERSION_STRING = "1.1-beta";
    
    final JFrame frame;
    final AboutBox aboutBox;
    
    public Main () {
	InstancePanel instancePanel = new InstancePanel();
	instancePanel.setPreferredSize(new Dimension(320, 150));
	
	SolvePanel solvePanel = new SolvePanel(instancePanel);
	solvePanel.setPreferredSize(new Dimension(320, 150));
	
        frame = new JFrame("PCP Solver");
	JComponent contentPane = (JComponent) frame.getContentPane();
	contentPane.setLayout(new BorderLayout(5, 5));
	contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(instancePanel, BorderLayout.PAGE_START);
	contentPane.add(solvePanel, BorderLayout.CENTER);
	
	aboutBox = new AboutBox(frame, VERSION_STRING);
	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setJMenuBar(createMenuBar());
	frame.pack();
        frame.setVisible(true);
    }
    
    public JMenuBar createMenuBar () {
	JMenuBar menuBar = new JMenuBar();
	JMenuItem about = new JMenuItem("About");
	about.addActionListener(new ActionListener() {
		public void actionPerformed (ActionEvent e) {
		    aboutBox.setVisible(true);
		}
	    });
	menuBar.add(about);
	return menuBar;
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
		new Main();
            }
        });
    }
}
