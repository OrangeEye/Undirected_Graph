package main;

import java.util.HashMap;

public class Node<T> {
	private T value;

	// Speichert die Verbindung zwischen den Knoten mit dem entsprechenden Weg
	private HashMap<Node<T>, Double> neighborNodes = new HashMap<Node<T>, Double>();
	private HashMap<Node<T>, Double> distances = new HashMap<Node<T>, Double>();

	// Variable ob die Verbindungen zu den Knoten neu berechnet werden muss
	private boolean outdated = true;

	Node(T value) {
		this.value = value;
	}

	/*
	 * public void addConnection(Node<T> node) { this.connections.put(node, -1.0); }
	 */

	protected T getValue() {
		return value;
	}

	protected HashMap<Node<T>, Double> getNeighborNodes() {
		return neighborNodes;
	}

	protected void addConnection(Node<T> node, Double edge) {
		if (node == null)
			return;

		neighborNodes.put(node, edge);
		node.addConnectionR(this, edge);
	}

	private void addConnectionR(Node<T> node, Double edge) {
		if (node == null)
			return;

		neighborNodes.put(node, edge);
	}

	protected double getEdgeValue(Node<T> node) {
		if (node == null)
			return 0;
		try {
			if (!neighborNodes.containsKey(node))
				throw new RuntimeException("Node ist nicht in connections enthalten");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return neighborNodes.get(node);

	}

	protected double getDistance(Node<T> node) {
		if (node == null)
			return 0;

		if(outdated) {
			this.setOutdated(false);
			updateEdges();
		}
		try {
			if (!distances.containsKey(node))
				throw new RuntimeException("Node ist nicht in connections enthalten");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return distances.get(node);

	}

	private void updateEdges() {

		HashMap<Node<T>, Double> visited = new HashMap<Node<T>, Double>();
		this.distances.clear();
		double shortestDistance = Double.MAX_VALUE;
		Node<T> shortestNeighbor = null;

		// Speichert die Distance zu allen Nachbarknoten vom Startknoten aus
		for (Node<T> neighbor : this.neighborNodes.keySet()) {
			this.distances.put(neighbor, this.getEdgeValue(neighbor));
			if (shortestNeighbor == null || this.getEdgeValue(neighbor) < shortestDistance) {
				shortestDistance = this.getEdgeValue(neighbor);
				shortestNeighbor = neighbor;
			}
		}

		// Springt in den kürzesten Knoten und macht da weiter
		visited.put(shortestNeighbor, shortestDistance);
		updateEdgesR(this, shortestNeighbor, visited, shortestDistance);


	}

	private void updateEdgesR(Node<T> origin, Node<T> currentNode, HashMap<Node<T>, Double> visited,
			double distanceToCurrent) {

		/**
		 * Speichert die Distance vom Startknoten zum aktuellen Knoten + zu dem nächsten
		 * Knoten, wenn die Knoten noch nicht betrachtet wurden oder der Weg kürzer ist
		 */
		for (Node<T> neighbor : currentNode.neighborNodes.keySet()) {
			if(neighbor.equals(origin))
				continue;
			double distance = currentNode.getEdgeValue(neighbor) + distanceToCurrent;
			if (!origin.distances.containsKey(neighbor))
				origin.distances.put(neighbor, distance);
			else if (currentNode.getEdgeValue(neighbor) + distanceToCurrent < origin.distances.get(neighbor)) {
				origin.distances.put(neighbor, distance);
			}
		}

		/**
		 * Sucht den kürzesten, unbesuchten Knoten um dort weiter zu machen
		 */
		Node<T> shortestUnvisited = null;
		double shortestUnvisitedDistance = Double.MAX_VALUE;
		for (Node<T> unvisitedNode : origin.distances.keySet()) {
			if (!visited.containsKey(unvisitedNode) && (shortestUnvisited == null  || origin.getDistance(unvisitedNode) < shortestUnvisitedDistance)) {
				shortestUnvisited = unvisitedNode;
				shortestUnvisitedDistance = origin.getDistance(unvisitedNode);
			}
		}

		if (shortestUnvisited != null) {
			visited.put(shortestUnvisited, shortestUnvisitedDistance);
			updateEdgesR(origin, shortestUnvisited, visited, shortestUnvisitedDistance);
		}

	}

	protected void setOutdated(boolean outdated) {
		this.outdated = outdated;
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}
	
	protected void removeConnection(Node<T> toRemove) {
		this.outdated=true;
		neighborNodes.remove(toRemove);

	}

}
