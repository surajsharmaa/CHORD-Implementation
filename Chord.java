package edu.uofm.chord;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chord extends JPanel {

	private static int numberOfMaxNodes = 15;
	private static List<Integer> nodes;
	private static int lookUpNodeIndex;
	private boolean isDelete;
	// private TreeSet<Integer>

	public Chord() {
		initialize();

	}

	public Chord(int numOfNodes, List<Integer> nodes, int lookUpNodeIndex) {
		numberOfMaxNodes = numOfNodes;
		this.nodes = nodes;
		this.lookUpNodeIndex = lookUpNodeIndex;
	}

	public static void main(String[] args) {
		System.out.println("Starting CHORD!");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Chord();
			}
		});
	}

	public static void initialize() {
		// create frame to hold all the gui
		final JFrame frame = new JFrame();
		frame.setTitle("CHORD IMPEMENTATION");

		// Look Up Node
		// label
		JLabel lookUpNodeLabel = new JLabel("Look Up a Node:");
		lookUpNodeLabel.setBounds(50, 10, 100, 30);
		lookUpNodeLabel.setForeground(Color.black);
		// input field
		final JTextField lookUpNodeInputField = new JTextField();
		lookUpNodeInputField.setBounds(150, 10, 60, 30);
		// button
		JButton lookUpNodeButton = new JButton("LookUp Node");
		lookUpNodeButton.setBounds(220, 10, 120, 30);
		// panel
		JPanel lookUpNodepanel = new JPanel();
		lookUpNodepanel.setBorder(BorderFactory.createLineBorder(Color.black));
		lookUpNodepanel.setBounds(40, 5, 320, 40);

		// delete node
		// label
		JLabel deleteNodeLabel = new JLabel("Delete a Node");
		deleteNodeLabel.setBounds(50 + 220 + 120 + 50, 10, 100, 30);
		lookUpNodeLabel.setForeground(Color.black);
		// input field
		final JTextField deleteNodeInputField = new JTextField();
		deleteNodeInputField.setBounds(50 + 220 + 120 + 150, 10, 60, 30);
		// button
		JButton deleteNodeButton = new JButton("Delete Node");
		deleteNodeButton.setBounds(50 + 220 + 120 + 220, 10, 120, 30);
		// panel
		JPanel deleteNodepanel = new JPanel();
		deleteNodepanel.setBorder(BorderFactory.createLineBorder(Color.black));
		deleteNodepanel.setBounds(50 + 370, 5, 320, 40);

		// Button
		JButton addNode = new JButton("Add Node");
		addNode.setBounds(50 + 220 + 120 + 220 + 120 + 50, 10, 120, 30);

		// TextArea
		final JTextArea resutArea = new JTextArea("Actions Result will display here!");
		resutArea.setBounds(50, 250, 350, 50);
		frame.getContentPane().add(resutArea);

		final Random rand = new Random();
		int node = rand.nextInt(numberOfMaxNodes);
		final List<Integer> tempNodesArray = new ArrayList<Integer>();

		addNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int addNewNode = rand.nextInt(numberOfMaxNodes);
				while (nodes.contains(addNewNode)) {
					addNewNode = rand.nextInt(numberOfMaxNodes);
				}
				int newNode = addNewNode +1;
				System.out.println("ADDING NEW NODE!" + newNode);
				resutArea.setText("ADDING NEW NODE: " + newNode);
				tempNodesArray.add(addNewNode);
				frame.add(new Chord(numberOfMaxNodes, tempNodesArray, 50));
				frame.repaint();
			}
		});

		deleteNodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int del_val = Integer.parseInt(deleteNodeInputField.getText()) - 1;
				for (int i = 0; i < tempNodesArray.size(); i++) {
					if (tempNodesArray.get(i) == del_val) {
						tempNodesArray.remove(i);
					}
				}
				int deleteNode = del_val + 1;
				System.out.println("DELETE NODE: " + deleteNode);
				resutArea.setText("DELETE NODE: " + deleteNode);
				frame.add(new Chord(numberOfMaxNodes, tempNodesArray, 50));
				frame.repaint();
			}
		});

		lookUpNodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int lookUpNode = Integer.parseInt(lookUpNodeInputField.getText()) - 1;
				System.out.println("Looking Up Node: " + lookUpNode);
				Collections.sort(nodes);
				System.out.println("Current Nodes Keys Identifiers: " + nodes);
				resutArea.setText("Current Nodes Keys Identifiers: " + nodes);
				if (nodes.contains(lookUpNode)) {
					// Because the successor of key is one of the nodes in the network, given key is
					// assigned that node itself
					int assignedNode = lookUpNode+1;
					System.out.println("Successor("+assignedNode+") = " + assignedNode);
					resutArea.setText("Successor("+assignedNode+") = " + assignedNode);
				} else {
					// lookup node is not the one of the nodes in the CHORD, so need to find
					// successor node
					boolean successorNodeFound = false;
					for (int node : nodes) {
						if (node > lookUpNode) {
							int assignedNode = node+1;
							int lookupInt = lookUpNode+1;
							System.out.println("FIRST ROUND: Assigned to node: " + assignedNode);
							resutArea.setText("Successor("+lookupInt+") = " + assignedNode);
							successorNodeFound = true;
							break;
						}
					}
					// if sucessor node is not found
					if (!successorNodeFound) {
						for (int node : nodes) {
							if (node < lookUpNode) {
								int assignedNode = node+1;
								int lookupInt = lookUpNode+1;
								System.out.println(" SECOND ROUND: Assigned to node: " + assignedNode);
								resutArea.setText("Successor("+lookupInt+") = " + assignedNode);
								successorNodeFound = true;
								break;
							}
						}
					}
				}
				frame.add(new Chord(numberOfMaxNodes, tempNodesArray, lookUpNode));
				frame.repaint();
			}
		});

		frame.getContentPane().add(lookUpNodeLabel);
		frame.getContentPane().add(deleteNodeLabel);
		frame.getContentPane().add(lookUpNodeInputField);
		frame.getContentPane().add(deleteNodeInputField);
		frame.getContentPane().add(addNode);
		frame.getContentPane().add(lookUpNodeButton);
		frame.getContentPane().add(deleteNodeButton);
		frame.getContentPane().add(lookUpNodepanel);
		frame.getContentPane().add(deleteNodepanel);

		if (tempNodesArray.isEmpty()) {
			tempNodesArray.add(node);
			frame.getContentPane().add(new Chord(numberOfMaxNodes, tempNodesArray, 50));
			frame.repaint();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setSize(1024,1024);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.pack();
		frame.setVisible(true);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int a, b = 1024 / 5;
		int r = 4 * 1024 / 5;
		int n = numberOfMaxNodes;
		List<Integer> tempList = new ArrayList<Integer>();

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		a = getWidth() / 2;
		b = getHeight() / 2;
		int m = Math.min(a, b);
		r = 4 * m / 5;
		int r2 = Math.abs(m - r) / 2;
		g2d.drawOval(a - r, b - r, 2 * r, 2 * r);
		g2d.setColor(Color.DARK_GRAY);

		for (int i = 0; i < n; i++) {
			tempList.add(0);
			if (lookUpNodeIndex != 50) {
				if (i == lookUpNodeIndex) {
					tempList.set(i, 1);
				}
			}
			for (int j = 0; j < nodes.size(); j++) {
				if (i == nodes.get(j)) {
					tempList.set(i, 2);
				}
			}
		}

		for (int i = 0; i < n; i++) {
			double t = 2 * Math.PI * i / n;
			int x = (int) Math.round(a + r * Math.cos(t));
			int y = (int) Math.round(b + r * Math.sin(t));
			g2d.drawOval(x - r2, y - r2, 2 * r2, 2 * r2);
			g2d.drawString(Integer.toString(i), x - 3, y + 6);

			if (tempList.get(i) == 2) { // represents key node
				g2d.setColor(Color.RED);
			} else if (tempList.get(i) == 1) { // represents lookup
				g2d.setColor(Color.ORANGE);
			} else {
				g2d.setColor(Color.DARK_GRAY);
			}
		}
	}

}
