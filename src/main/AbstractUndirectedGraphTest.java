package main;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public abstract class AbstractUndirectedGraphTest {
	public abstract IUndirectedGraph<Character> get();
	
	IUndirectedGraph<Character> g;
	
	@Before
	public void setUp() {
		g = get();
	}
	
	@Test
	public void testGraph1() {
		g.addNode( 'a' );
		g.addNode( 'b' );
		g.addNode( 'c' );
		g.addNode( 'd' );
		
		g.addEdge( 0, 1 );
		g.addEdge( 1, 2 );
		g.addEdge( 1, 3 );
		
		assertEquals( Arrays.asList( new Character[] { 'a', 'b', 'c', 'd' } ), g.bfs( 0 ) );
		assertEquals( Arrays.asList( new Character[] { 'a', 'b', 'c', 'd' } ), g.dfs( 0 ) );
		
		assertEquals( Arrays.asList( new Character[] { 'c', 'b', 'a', 'd' } ), g.bfs( 2 ) );
		
		assertEquals( Arrays.asList( new Character[] { 'd', 'b', 'a', 'c' } ), g.dfs( 3 ) );
		
		assertTrue( g.isTree() );
		assertTrue( g.isConnected() );
		assertFalse( g.hasCycles() );
	}
	
	@Test
	public void testGraph2() {
		g.addNode( 'a' );
		g.addNode( 'b' );
		g.addNode( 'c' );
		g.addNode( 'd' );
		
		g.addEdge( 0, 1 );
		g.addEdge( 2, 0 );
		g.addEdge( 1, 2 );
		g.addEdge( 1, 3 );
		
		assertEquals( Arrays.asList( new Character[] { 'a', 'b', 'c', 'd' } ), g.bfs( 0 ) );
		assertEquals( Arrays.asList( new Character[] { 'a', 'b', 'c', 'd' } ), g.dfs( 0 ) );
		
		assertEquals( Arrays.asList( new Character[] { 'c', 'a', 'b', 'd' } ), g.bfs( 2 ) );
		
		assertEquals( Arrays.asList( new Character[] { 'd', 'b', 'a', 'c' } ), g.dfs( 3 ) );
		
		assertFalse( g.isTree() );
		assertTrue( g.isConnected() );
		assertTrue( g.hasCycles() );
	}
	
	@Test
	public void testGraph3() {	
		g.addNode( 'a' );
		g.addNode( 'b' );
		g.addNode( 'c' );
		g.addNode( 'd' );
		g.addNode( 'e' );
		g.addNode( 'f' );
		
		// a
		g.addEdge( 0, 1 );
		g.addEdge( 0, 2 );
		g.addEdge( 0, 3 );
		g.addEdge( 0, 5 );
		
		// b
		g.addEdge( 1, 2 );
		g.addEdge( 1, 3 );
		g.addEdge( 1, 4 );
		
		// c
		g.addEdge( 2, 4 );
		g.addEdge( 2, 5 );
		
		// d
		g.addEdge( 3, 4 );
		g.addEdge( 3, 5 );
		
		// e
		g.addEdge( 4, 5 );
			
		assertEquals( Arrays.asList( new Character[] { 'a', 'b', 'c', 'd', 'f', 'e' } ), g.bfs( 0 ) );
		assertEquals( Arrays.asList( new Character[] { 'a', 'b', 'c', 'e', 'd', 'f' } ), g.dfs( 0 ) );
		
		assertEquals( Arrays.asList( new Character[] { 'd', 'a', 'b', 'e', 'f', 'c' } ), g.bfs( 3 ) );
		
		assertEquals( Arrays.asList( new Character[] { 'f', 'a', 'b', 'c', 'e', 'd' } ), g.dfs( 5 ) );
		
		assertFalse( g.isTree() );
		assertTrue( g.isConnected() );
		assertTrue( g.hasCycles() );
	}
	
	@Test
	public void testGraph4() {	
		g.addNode( 'A' );
		g.addNode( 'B' );
		g.addNode( 'C' );
		g.addNode( 'D' );
		g.addNode( 'E' );
		g.addNode( 'F' );
		
		g.addEdge( 0, 0 );
		g.addEdge( 0, 1 );
		g.addEdge( 2, 3 );
		g.addEdge( 2, 4 );
		g.addEdge( 3, 4 );
		g.addEdge( 3, 5 );
		g.addEdge( 4, 5 );
		g.addEdge( 5, 5 );
				
		assertEquals( Arrays.asList( new Character[] { 'A', 'B' } ), g.bfs( 0 ) );
		assertEquals( Arrays.asList( new Character[] { 'B', 'A' } ), g.bfs( 1 ) );
	
		assertEquals( Arrays.asList( new Character[] { 'C', 'D', 'E', 'F' } ), g.bfs( 2 ) );

		assertEquals( Arrays.asList( new Character[] { 'E', 'C', 'D', 'F' } ), g.dfs( 4 ) );
		
		assertFalse( g.isTree() );
		assertFalse( g.isConnected() );
		assertTrue( g.hasCycles() );
	}
	
	@Test
	public void testGraph5() {
		g.addNode( 'A' );
		g.addNode( 'B' );
		g.addNode( 'C' );
		g.addNode( 'D' );
		g.addNode( 'E' );
		
		g.addEdge( 0, 1 );
		g.addEdge( 1, 2 );
		g.addEdge( 2, 3 );
		
		g.addEdge( 4, 1 );
		g.addEdge( 3, 4 );
				
		assertEquals( Arrays.asList( new Character[] { 'A', 'B', 'C', 'E', 'D' } ), g.bfs( 0 ) );
		assertEquals( Arrays.asList( new Character[] { 'B', 'A', 'C', 'E', 'D' } ), g.bfs( 1 ) );
	
		assertEquals( Arrays.asList( new Character[] { 'D', 'C', 'E', 'B', 'A' } ), g.bfs( 3 ) );

		assertEquals( Arrays.asList( new Character[] { 'E', 'B', 'A', 'C', 'D' } ), g.dfs( 4 ) );
		
		assertFalse( g.isTree() );
		assertTrue( g.isConnected() );
		assertTrue( g.hasCycles() );
	}
}
