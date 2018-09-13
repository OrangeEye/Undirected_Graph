package main;

import java.util.ArrayList;

public class Node<T> {
	private T value;
	private ArrayList<Node<T>> connections = new ArrayList<Node<T>>();
	
	Node(T value){
		this.value=value;
	}
	
	public void addConnection(Node<T> node) {
		this.connections.add(node);
	}

	public T getValue() {
		return value;
	}


	public ArrayList<Node<T>> getConnections() {
		return connections;
	}


}
