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
import java.util.LinkedList;
import java.util.Iterator;
import javax.swing.*;

import drc.jsearch.*;
import drc.pcpsolver.pcp.*;

public class SolvePanel extends JPanel implements ActionListener
{
    static final int REPORT_INTERVAL = 500;
    static final int INITIAL_REPORT_DELAY = REPORT_INTERVAL;
    static final int CACHE_SIZE = 100000;
    static final double DEFAULT_MAX_DEPTH = 50.0;

    // final JTextField maxSolutionLengthTextField;
    final JButton solveButton, cancelButton;
    final JTextArea outputTextArea;
    final InstancePanel instancePanel;

    final Timer timer;
    final Solver solver;
    
    SearchTask searchTask;
    long startTime;
    
    public SolvePanel (InstancePanel instancePanel) {
	this.instancePanel = instancePanel;
	
	setLayout(new BorderLayout(5, 5));

	JPanel panel = new JPanel();
	// panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
	// panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	// panel.add(new JLabel ("Max. solution length: "));
	// maxSolutionLengthTextField = new JTextField ("10");
	// panel.add(maxSolutionLengthTextField);
	// add(panel, BorderLayout.PAGE_START);

	JPanel buttonPane = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
	panel.add(Box.createHorizontalGlue());

	solveButton = new JButton("Solve");
	solveButton.setActionCommand("solve");
	solveButton.addActionListener(this);

	cancelButton = new JButton("Cancel");
	cancelButton.setActionCommand("cancel");
	cancelButton.addActionListener(this);
	cancelButton.setEnabled(false);
	
	panel.add(solveButton);
	panel.add(cancelButton);
	add(panel, BorderLayout.PAGE_START);

	outputTextArea = new JTextArea(1, 1);
	outputTextArea.setEditable(false);
	add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

	timer = new Timer(REPORT_INTERVAL, this);
	timer.setInitialDelay(INITIAL_REPORT_DELAY);
	timer.setActionCommand("timer");
	
	solver = new Solver();	
    }

    String solutionToString (LinkedList<ActionInterface> actions) {
	String top = "", bottom = "";
	Iterator<ActionInterface> it = actions.iterator();
	PcpAction pcpAction = (PcpAction) it.next();
	top = pcpAction.domino.top;
	bottom = pcpAction.domino.bottom;
	while (it.hasNext()) {
	    top = top.concat(" | ");
	    bottom = bottom.concat(" | ");
	    pcpAction = (PcpAction) it.next();
	    top = top.concat(pcpAction.domino.top);
	    bottom = bottom.concat(pcpAction.domino.bottom);
	}
	return top + "\n" + bottom + "\n";
    }

    void printSolution (Node solution) {
	if (solution != null) {
	    LinkedList<ActionInterface> actions = solution.actionsTo();
	    outputTextArea.setText("The PCP instance has a match of length "
				   + actions.size() + ":\n");
	    outputTextArea.append(solutionToString(actions));
	} else {
	    outputTextArea.setText("The PCP instance has no match.\n" +
				   "All possibilities have been examined.");
	}
    }

    void printSearchStatistics () {
	// IterativeDeepeningSearch algo
	//     = (IterativeDeepeningSearch) searchAlgorithm;
	long elapsedTime = System.currentTimeMillis() - startTime;
	outputTextArea.setText("Searching for " + elapsedTime / 1000 + " seconds.");
	// outputTextArea.append("iteration: " + algo.currentIteration() + "\n");
	// outputTextArea.append("nodes: " + algo.nofNodesVisited() / 1000 + "K\n");
	// outputTextArea.append("depth: " + algo.currentDepth() + "\n");
	// outputTextArea.append("memory: " + algo.memoryUse() + "\n");
	// outputTextArea.append("cache: " + algo.cacheSize() + "\n");
	// outputTextArea.append("hits: " + algo.nofClosedListHits() + "\n");
    }

    class SearchTask extends SwingWorker<Node, Void>
    {
	final Pcp pcp;
	final int maxDepth;
	
	SearchTask (Pcp pcp, int maxDepth) {
	    this.pcp = pcp;
	    this.maxDepth = maxDepth;
	}
	
	@Override
	public Node doInBackground() {
	    return solver.findMatch(pcp, maxDepth);
	}

	@Override
	public void done() {
	    cancelButton.setEnabled(false);
	    timer.stop();
	    if (isCancelled()) {
		return;
	    }
	    try {
		printSearchStatistics();
		printSolution(get());
	    } catch (InterruptedException ignore) {
		;	    
	    } catch (java.util.concurrent.ExecutionException e) {
		String why = null;
		Throwable cause = e.getCause();
		if (cause != null) {
		    why = cause.getMessage();
		} else {
		    why = e.getMessage();
		}
		System.err.println("Error solving PCP instance: " + why);
	    }
	    solveButton.setEnabled(true);
	}
    }
    
    public void actionPerformed (ActionEvent e) {
	String actionCommand = e.getActionCommand();
	if ("solve".equals(actionCommand)) {
	    solveButton.setEnabled(false);
	    outputTextArea.setText("");
	    Pcp pcp = instancePanel.getPcpInstance();
	    Solver.ReasonNoSolution reason = solver.findReasonNoSolution(pcp);
	    if (reason != null) {
		outputTextArea.setText("PCP instance has no match.\n"
				       + reason.explanation());
		solveButton.setEnabled(true);
	    } else {
		searchTask = new SearchTask(pcp, DEFAULT_MAX_DEPTH);
		startTime = System.currentTimeMillis();
		timer.start();
		searchTask.execute();
	    }
	    cancelButton.setEnabled(true);
	} else if ("cancel".equals(actionCommand)) {
	    cancelButton.setEnabled(false);
	    timer.stop();
	    solver.cancel(true);
	    searchTask.cancel(true);
	    outputTextArea.setText("Search cancelled.");
	    solveButton.setEnabled(true);
	} else if ("timer".equals(actionCommand)) {
	    printSearchStatistics();
	}
    }
}
