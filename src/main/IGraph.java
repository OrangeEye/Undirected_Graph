package main;

import java.util.List;

public interface IGraph<T> {
	void addNode( T value );
	void addEdge( int node1Index, int node2Index );
	
	List<T> bfs( int startIndex );
	List<T> dfs( int startIndex );

	boolean isConnected();
	boolean hasCycles();
	boolean isTree();
}
