package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import model.Position;

public class GeneticMazeSelector {
	private LinkedHashMap<Maze, Double> population;
	private Maze selected;
	private int selectedSize;
	private int crossoverSize;
	private int mutateSize;

	public GeneticMazeSelector(int populationSize, int mazeSize, float selectionRate, float crossoverRate, float mutationRate) {
		generateRandomPopulation(populationSize, mazeSize);
		selectedSize = (int)(populationSize * selectionRate);
		crossoverSize = (int)(populationSize * crossoverRate);
		mutateSize = (int)(populationSize * mutationRate);
		selected = null;
	}
	
	public GeneticMazeSelector(int populationSize) {
		generateRandomPopulation(populationSize, 50);
	}
	
	public void generateRandomPopulation(int populationSize, int mazeSize) {
		MazeFitnessFunction fitness = new MazeFitnessFunction();
		population = new LinkedHashMap<Maze, Double>();
		
		for (int i = 0; i < populationSize; i++) {
			Maze maze = new Maze(mazeSize);
			population.put(maze, fitness.eval(maze));
		}
	}
	
	public void updatePopulation(List<Maze> generation) {
		MazeFitnessFunction fitness = new MazeFitnessFunction();
		population = new LinkedHashMap<Maze, Double>();
		
		for (Maze maze : generation) {
			population.put(maze, fitness.eval(maze));
		}
	}
	
	public List<Maze> selectGenerationCandidates() {
		List<Maze> selected = new ArrayList<Maze>(population.keySet());
		
		Collections.sort(selected, new Comparator<Maze>() {

			@Override
			public int compare(Maze m1, Maze m2) {
				return (int)(population.get(m1) - population.get(m2));
			}
			
		});
		
		return selected;//.subList(0, selectedSize);
	}
	
	public List<Maze> crossoverGeneration(List<Maze> selected) {
		List<Maze> crossoverPopulation = new ArrayList<Maze>();
		Collections.shuffle(selected);
		
		for (int i = 0; i < crossoverSize; i++) {
			Maze parent0 = selected.remove(0);
			Maze parent1 = selected.remove(0);
			Maze child = parent0.merge(parent1);
			
			crossoverPopulation.add(child);
		}
		
		selected.addAll(crossoverPopulation);
		System.out.println(selected.size());
		return selected;
	}
	
	public List<Maze> mutateGeneration(List<Maze> selected) {
		List<Maze> mutatedPopulation = new ArrayList<Maze>();
		Collections.shuffle(selected);
		
		for (int k = 0; k < mutateSize; k++) {
			Random random = new Random();
			Maze maze = selected.remove(0);
			
			int mazeSize = maze.getSize();
			int i = random.nextInt() % mazeSize;
			int j = random.nextInt() % mazeSize;
			maze.addSlot(new Position(i, j));
			
			mutatedPopulation.add(maze);
		}
		
		selected.addAll(mutatedPopulation);
		
		return selected;
	}
	
	public void newGeneration() {
		List<Maze> selected = selectGenerationCandidates();
		List<Maze> crossover = crossoverGeneration(selected);
		List<Maze> mutated = mutateGeneration(crossover);
		updatePopulation(mutated);
	}

	public Maze getSelectedMaze() {
		return selected;
	}

	public boolean hasSelectedMaze() {
		MazeFitnessFunction function = new MazeFitnessFunction();
		selected = Collections.max(population.keySet(), new Comparator<Maze>() {

			@Override
			public int compare(Maze o1, Maze o2) {
				return population.get(o1).compareTo(population.get(o2));
			}
			
		});
		
		return function.eval(selected) >= 88;
	}
}
