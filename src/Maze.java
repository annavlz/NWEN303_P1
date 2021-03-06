import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Maze {
	final int EXIT = 0;
	final int NEW = 1;
	final int LIVE = 2;
	final int DEADEND = 3;
	final int WALL = 4;
	Cell[][] mazeArray;
	int entryX;
	int entryY;
	Cell entry;
	Cell exit;
	boolean exitFound = false;
	StopWatch w = new StopWatch();
	
	public Maze(Cell[][] mazeArray, int entryX, int entryY) {
		this.mazeArray = mazeArray;
		this.entryX = entryX;
		this.entryY = entryY;
		createMaze();
	}

	private void createMaze() {
		this.entry = mazeArray[entryY][entryX];
		this.entry.isEntry = true;
		this.entry.status = EXIT;
		for(int y = 0; y < mazeArray.length; y++){
			for(int x = 0; x < mazeArray.length; x++){
				Cell cell = mazeArray[y][x];
				addNbr(cell, mazeArray, y-1,x);
				addNbr(cell, mazeArray,y+1,x);
				addNbr(cell, mazeArray,y,x-1);
				addNbr(cell, mazeArray,y,x+1);
			}
		}		
	}

	private void addNbr (Cell me, Cell[][] mazeArray, int y, int x){
		int size = mazeArray.length;
		if(x >= 0 &&  y >= 0 && x < size && y < size){
			me.nbrs.add(mazeArray[y][x]);
		}
	}
	public void findExit() throws InterruptedException {	
		entry.fNum = Main.startParty;
		start(this.entry);	

	}
	
	public void printMaze() {
		for(int y = 0; y < mazeArray.length; y++){
			String mazeS = "";
			for(int x = 0; x < mazeArray.length; x++){
				Cell cell = mazeArray[y][x];
				if(cell.status == WALL){
					mazeS += "X";
				}else if (cell.status == EXIT){
					mazeS += "*";
				}else{
					mazeS += " ";
				}
			}
			System.out.println(mazeS);
		}
	}

	public void start (Cell from) throws InterruptedException {
		Action action = new Action(from, this); 
		Thread move = new Thread(action);
		w.start();
		move.start();
	}
}
