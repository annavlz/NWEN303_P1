import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Maze {
	Cell[][] mazeArray;
	int entryX;
	int entryY;
	Cell entry;
	Cell exit;
	
	public Maze(Cell[][] mazeArray, int entryX, int entryY) {
		this.mazeArray = mazeArray;
		this.entryX = entryX;
		this.entryY = entryY;
		createMaze();
	}

	private void createMaze() {
		this.entry = mazeArray[entryY][entryX];
		this.entry.isEntry = true;
		for(int y = 0; y < mazeArray.length; y++){
			for(int x = 0; x < mazeArray.length; x++){
				Cell cell = mazeArray[y][x];
				addNbr(cell, mazeArray, y-1,x);
				addNbr(cell, mazeArray,y+1,x);
				addNbr(cell, mazeArray,y,x-1);
				addNbr(cell, mazeArray,y,x+1);
//				System.out.println(cell);
			}
		}		
	}

	private void addNbr (Cell me, Cell[][] mazeArray, int y, int x){
		int size = mazeArray.length;
		if(x >= 0 &&  y >= 0 && x < size && y < size){
			me.nbrs.add(mazeArray[y][x]);
		}
	}
	public void findExit(Maze maze, int fNum) {
		entry.fNum = fNum;
		start(this.entry);	
	}
	
	public void start (Cell from) {
		Action action = new Action(from); 
		Thread move = new Thread(action);
		move.start();
	}
}
