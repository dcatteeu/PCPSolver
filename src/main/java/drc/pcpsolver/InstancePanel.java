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

import drc.pcpsolver.pcp.*;
                        
public class InstancePanel extends JPanel
{
    final JTextArea textArea;

    InstancePanel () {
	//setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	setLayout(new BorderLayout(5, 5));
	
	add(new JLabel("<html>Define the PCP instance below. Define one" +
		       " domino per line. Each domino is defined by the" +
		       " concatenation of its top string, a forward slash" +
		       " (/), and its bottom string. For example: 01/111." +
		       "</html>"),
	    BorderLayout.PAGE_START);
	
	textArea = new JTextArea(5, 5);
	textArea.setText("100/1\n0/100\n1/0");
	JScrollPane scrollPane = new JScrollPane(textArea);
	textArea.setEditable(true);
	add(scrollPane, BorderLayout.CENTER);
    }

    // Return the domino represented by string. Assume top and bottom
    // string are separated by a forward slash (/).
    Domino stringToDomino(String string) {
	String[] parts = string.split("/");
	return new Domino(parts[0], parts[1]);
    }

    // Return the array of currently defined dominoes. Assume 1 domino
    // per line.
    Domino[] getDominoes () {
	String allText = textArea.getText();
	String[] lines = allText.split("\n");
	Domino[] dominoes = new Domino[lines.length];
	for (int i=0; i < lines.length; i++) {
	    dominoes[i] = stringToDomino(lines[i]);
	}
	return dominoes;
    }

    // Return the current PCP instance.
    Pcp getPcpInstance() {
	Domino[] dominoes = getDominoes();
	Pcp pcp = new Pcp(dominoes);
	return pcp;
    }
}
