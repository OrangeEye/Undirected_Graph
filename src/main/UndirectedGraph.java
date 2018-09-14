package main;

import java.util.ArrayList;

public class UndirectedGraph<T> extends Graph<T> implements IUndirectedGraph<T> {
	private ArrayList<Node<T>> visited = new ArrayList<Node<T>>();

	/**
	 * Stellt eine Kante mit einem Wert zwischen den Knoten mit den entsprechenden
	 * Werten her.
	 */
	@Override
	public void addEdge(T value1, T value2, double edge) {
		if (value1 == null || value2 == null || edge <= 0 || !nodes.contains(getNode(value1)) || !nodes.contains(getNode(value2))) {
			return;
		}
		
		getNode(value1).addConnection(getNode(value2), edge);
		for(Node<T> node : nodes) {
			node.setOutdated(true);
		}

	}

	@Override
	public boolean isConnected() {
		for (Node<T> node : nodes) {
			bfs(nodes.indexOf(node));
			if (bfs.size() == nodes.size())
				return true;
		}
		return false;
	}

	@Override
	public boolean hasCycles() {
		if (!nodes.isEmpty()) {
			boolean result = false;
			// Macht eine Tiefensuche bei jedem Knoten und gibt boolean zurück ob ein Zyklus
			// da ist
			for (Node<T> node : nodes) {
				visited.clear();
				result = hasCyclesR(node, null);
				if (result)
					return result;
			}
		}
		return false;
	}

	private boolean hasCyclesR(Node<T> node, Node<T> previous) {
		if (visited.contains(node))
			return true;
		visited.add(node);
		boolean result = false;
		for (Node<T> connection : node.getNeighborNodes().keySet()) {
			if (!connection.equals(previous))
				result = hasCyclesR(connection, node);
			if (result)
				return result;
		}
		// Löscht den letzten Punkt, wenn keine Kante mehr da ist. Damit ist visited
		// immer ein Weg und sobald ein Knoten doppelt vorkommt ist ein Zyklus vorhanden
		visited.remove(visited.size() - 1);
		return false;
	}

	@Override
	public boolean isTree() {
		// TODO Auto-generated method stub
		return isConnected() && !hasCycles();
	}

	/**
	 * Gibt die Länge der Kante zwischen zwei Knote zurück
	 */

	protected double getEdgeValue(T value1, T value2) {
		if (value1 == null || value2 == null)
			return -1;
		
		return getNode(value1).getEdgeValue(getNode(value2));
		
	}


	protected Node<T> getNode(T value) {
		if (value == null)
			return null;
		
		
		for(Node<T> node : nodes) {
			if (node.getValue().equals(value)) {
				return node;
			}
		}
		return null;
	}

	@Override
	public void remove(T value) {
		Node<T> toRemove = getNode(value);
		nodes.remove(toRemove);
		for(Node<T> node : nodes) {
			node.removeConnection(toRemove);
		}
	}

	@Override
	public double getDistance(T value1, T value2) {
		if (value1 == null || value2 == null)
			return -1;
		
		return getNode(value1).getDistance((getNode(value2)));
	}
	


}
