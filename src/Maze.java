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
		for(int y = 0; y < mazeArray.length-1; y++){
			for(int x = 0; x < mazeArray.length-1; x++){
				Cell cell = mazeArray[y][x];
				cell.nbrs.add(getCell(mazeArray, y-1,x));
				cell.nbrs.add(getCell(mazeArray,y+1,x));
				cell.nbrs.add(getCell(mazeArray,y,x-1));
				cell.nbrs.add(getCell(mazeArray,y,x+1));
			}
		}		
	}

	private Cell getCell (Cell[][] mazeArray, int x, int y){
		int size = mazeArray.length;
		Cell cell;
		if(x > 0 &&  y > 0 && x < size && y < size){
			cell = mazeArray[y][x];
		}else{
			cell = null;
		}
		return cell;
	}
	public void findExit(Maze maze, int fNum) {
		entry.fNum = fNum;
		chooseWay(this.entry);	
	}
	
	
	private void chooseWay(Cell from) {
		ArrayList<Cell> options = findOptions(this.entry);
		if(options.size() == 1){
			go(from, options.get(0), from.fNum);
		} else if (options.size() > 1){
			divide (from, from.fNum, options);
		} else {
			if(from.isEdge && from.fNum == this.entry.fNum){
				getPath(from);
				System.out.println("To the pub!");
			} else{
				turnBack(from);
			}
		}
	}
	private void getPath (Cell from) {
		System.out.println(from.parent);
		while(from.parent != null){
			System.out.println(from);
			getPath(from.parent);
		}
	}
	
	private void turnBack(Cell from) {
		ActionB _return = new ActionB (from); 
		Thread goBack = new Thread(_return);
		goBack.start();
		//move.join();
		if(_return.canProceed){
			turnBack(from.parent);
		}else{
			ArrayList<Cell>options = findOptions(from);
			if(options.size() > 1){
				chooseWay(from);
			}
		}
		
	}

	private void divide (Cell from, int group, ArrayList<Cell>options){
		if (group > 0){
			int party = group / options.size();
			if(party == 0){
				party = 1;
			}
			go(from, options.get(0), party);
			options.remove(0);
			divide(from, group - party, options);
		}
	}
	
	public void go (Cell from, Cell to, int howMany) {
		ActionF action = new ActionF (from, to, howMany); 
		Thread move = new Thread(action);
		move.start();
		//move.join();
		if(action.canProceed){
			chooseWay(to);
		} 
	}
	
	public static ArrayList<Cell> findOptions (Cell point){
		ArrayList<Cell> options = new ArrayList<Cell>();
			for(Cell nbr : point.nbrs){
				if(nbr.exitPath == true){
					ArrayList<Cell>exitPath = new ArrayList<Cell>();
					exitPath.add(nbr);
					return exitPath;
				}
				if(!(nbr == null) && !nbr.isWall && !nbr.deadEnd){
					options.add(nbr);
				}
			}
		return options;
	}
}
