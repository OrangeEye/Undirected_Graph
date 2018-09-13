package main;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public abstract class Graph<T> implements IGraph<T> {
	protected ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
	protected ArrayList<Node<T>> dfs = new ArrayList<Node<T>>();
	protected ArrayList<Node<T>> bfs = new ArrayList<Node<T>>();
	protected Deque<Node<T>> tempBfs = new ArrayDeque<Node<T>>();

	@Override
	public void addNode(T value) {
		nodes.add(new Node<T>(value));
	}

	@Override
	public List<T> bfs(int startIndex) {
		if (startIndex >= 0 && startIndex < nodes.size()) {
			bfs.clear();
			bfs.add(nodes.get(startIndex));
			breadthFirstR(nodes.get(startIndex));
			ArrayList<T> list = new ArrayList<T>();
			for (Node<T> bfsNode : bfs)
				list.add(bfsNode.getValue());
			return list;
		}
		return null;
	}

	private void breadthFirstR(Node<T> node) {
		//bfs.add(node);
		//tempBfs.add(node);
		for (Node<T> connection : node.getConnections())
			if (!bfs.contains(connection) && !tempBfs.contains(connection)) {
				tempBfs.addLast(connection);
			}

		// Führt eine BreitenSuche bei den Verbindungen durch, die noch nicht in der
		// Liste sind (um Zyklen zu vermeiden)
		if(!tempBfs.isEmpty()) {
			bfs.add(tempBfs.getFirst());
			breadthFirstR(tempBfs.pollFirst());
		}
		
		
	/*	for (Node<T> tempNode : tempBfs) {
			for (Node<T> connection : tempNode.getConnections())
				if (!bfs.contains(connection))
					breadthFirstR(connection);
		} */
	}

	@Override
	public List<T> dfs(int startIndex) {
		if (startIndex >= 0 && startIndex < nodes.size()) {
			dfs.clear();
			deepFirstSearch(nodes.get(startIndex));
			ArrayList<T> list = new ArrayList<T>();
			for (Node<T> dsfNode : dfs)
				list.add(dsfNode.getValue());
			return list;
		}
		return null;
	}

	private void deepFirstSearch(Node<T> node) {

		dfs.add(node);
		for (Node<T> connection : node.getConnections()) {
			if (!dfs.contains(connection))
				deepFirstSearch(connection);
		}

	}

}
