import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Maze {
	Cell[][] mazeArray;
	int entryX;
	int entryY;
	Cell entry;
//	private Map<Coords, Cell> maze = new HashMap<Coords, Cell>();
	
	public Maze(Cell[][] mazeArray, int entryX, int entryY) {
		this.mazeArray = mazeArray;
		this.entryX = entryX;
		this.entryY = entryY;
		createMaze();
	}

	private void createMaze() {
		entry = mazeArray[entryY][entryX];
		for(int y = 0; y < mazeArray.length; y++){
			for(int x = 0; x < mazeArray.length; x++){
				Cell cell = mazeArray[y][x];
				Coords coords = new Coords(x+1, y+1);
				cell.nbrs.add(mazeArray[y-1][x]);
				cell.nbrs.add(mazeArray[y+1][x]);
				cell.nbrs.add(mazeArray[y][x-1]);
				cell.nbrs.add(mazeArray[y][x+1]);
//				maze.put(coords, cell);
			}
		}		
	}


	public void findExit(Maze maze, int fNum) {
		entry.fNum = fNum;
		entry.visited = true;
		ArrayList<Cell> options = findOptions(this.entry);
		chooseWay(this.entry, options);	
	}
	
	
	private void chooseWay(Cell from, ArrayList<Cell> options) {
		if(options.size() == 1){
			move(from, options.get(0), from.fNum);
		}
		
	}

	private void move(Cell from, Cell to, int howMany) {
		to.parent = from;
		to.fNum = howMany;
		from.fNum =- howMany;
		to.visited = true;	
	}

	public ArrayList<Cell> findOptions (Cell point){
		ArrayList<Cell> options = new ArrayList<Cell>();
			for(Cell nbr : point.nbrs){
				if(nbr.exitPath == true){
					ArrayList<Cell>exit = new ArrayList<Cell>();
					exit.add(nbr);
					return exit;
				}
				if(!(nbr == null) && !nbr.isWall && !nbr.visited){
					options.add(nbr);
				}
			}
		return options;
	}
	
}
