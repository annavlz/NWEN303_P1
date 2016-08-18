import java.util.HashMap;
import java.util.Map;

public class Maze {
	Cell[][] mazeArray;
	int entryX;
	int entryY;
	private Map<Coords, Cell> maze = new HashMap<Coords, Cell>();;
	
	public Maze(Cell[][] mazeArray, int entryX, int entryY) {
		this.mazeArray = mazeArray;
		this.entryX = entryX;
		this.entryY = entryY;
		createMaze();
	}

	private void createMaze() {
		mazeArray[entryY][entryX].isEntry = true;
		for(int y = 0; y < mazeArray.length; y++){
			for(int x = 0; x < mazeArray.length; x++){
				Cell cell = mazeArray[y][x];
				Coords coords = new Coords(x+1, y+1);
				cell.northN = mazeArray[y-1][x];
				cell.southN = mazeArray[y+1][x];
				cell.westN = mazeArray[y][x-1];
				cell.eastN = mazeArray[y][x+1];
				maze.put(coords, cell);
			}
		}
		
	}



	public void findExit(Maze maze, String string) {
		
	}
	
}
