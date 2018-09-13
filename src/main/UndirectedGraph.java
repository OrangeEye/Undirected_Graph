package main;

import java.util.ArrayList;

public class UndirectedGraph<T> extends Graph<T> implements IUndirectedGraph<T> {
	private ArrayList<Node<T>> visited = new ArrayList<Node<T>>();
	@Override
	public void addEdge(int node1Index, int node2Index) {
		if (node1Index >= 0 && node1Index < nodes.size() && node2Index >= 0 && node2Index < nodes.size()) {
			nodes.get(node1Index).addConnection(nodes.get(node2Index));
			nodes.get(node2Index).addConnection(nodes.get(node1Index));
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
				result = hasCyclesR(node,null);
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
		for (Node<T> connection : node.getConnections()) {
			if(!connection.equals(previous))
			result = hasCyclesR(connection, node );
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

}
