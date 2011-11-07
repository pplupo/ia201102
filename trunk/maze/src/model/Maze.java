package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Maze {
	private Graph<Position> graph;
	private int size;

	public Position id(int i, int j) {
		return new Position(i, j);
	}
	
	public Slot<Position> getSlot(Position position) {
		return graph.getVertex(position);
	}
	
	public Maze(int size) {
		this.graph = new Graph<Position>();
		this.size = size;
		
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (j+1 < size && rand.nextInt() % 8 == 0)
					graph.addEdge(id(i, j), id(i, j+1));
				
				if (i+1 < size && rand.nextInt() % 8 == 0)
					graph.addEdge(id(i, j), id(i+1, j));
			}
		}
	}
	
	private Maze(Graph<Position> graph, int size) {
		this.graph = graph;
		this.size = size;
	}

	public Maze merge(Maze maze) {
		List<Position> belongToEither = new ArrayList<Position>();
		
		for (Position p : graph.getVertexKeys()) {
			if (maze.containsSlot(p)) {
				belongToEither.add(p);
			}
		}
		
		if (!belongToEither.isEmpty()) {
			Collections.shuffle(belongToEither);
			
			Position position = belongToEither.get(0);
			int column = position.getColumn();
			
			Graph<Position> merged;
			merged = Graph.addSubGraph(0, column, graph);
			merged = Graph.addSubGraph(column, size, merged, maze.graph);
			
			return new Maze(merged, size);
		}
		
		return this;
	}
	
	private boolean containsSlot(Position p) {
		return graph.hasVertex(p);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (graph.hasVertex(id(i, j))) {
					builder.append('.');
				}
				else {
					builder.append('X');
				}
				builder.append(' ');
			}
			builder.append('\n');
		}
		
		return builder.toString();
	}

	public int getSize() {
		return size;
	}

	public void addSlot(Position p) {
		graph.addVertex(p);
		
		if (graph.hasVertex(p.getNorth()))
			graph.addEdge(p, p.getNorth());
		
		if (graph.hasVertex(p.getSouth()))
			graph.addEdge(p, p.getSouth());
		
		if (graph.hasVertex(p.getEast()))
			graph.addEdge(p, p.getEast());
		
		if (graph.hasVertex(p.getWest()))
			graph.addEdge(p, p.getWest());
	}

	public Slot<Position> getBeginning() {
		Slot<Position> vertex = null;
		for (int j = 0; j<50; j++) {
			vertex = getSlot(new Position(0,j));
			if (vertex != null) {
				return vertex;
			}
		}
		return null;
	}
}
