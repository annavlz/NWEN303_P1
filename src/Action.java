import java.util.ArrayList;
import java.util.Collections;

public class Action implements Runnable {
	final int EXIT = 0;
	final int NEW = 1;
	final int LIVE = 2;
	final int DEADEND = 3;
	final int WALL = 4;
	Cell from;
	Cell to;
	int howMany;
	Maze maze;
	
	
	public Action(Cell from, Maze maze){
		this.from = from;
		this.maze = maze;
	}
	
	
	public Action(Cell from, Cell to, int howMany, Maze maze){		
		this.from = from;
		this.to = to;
		this.howMany = howMany;
		this.maze = maze;
	}
	
	
	public void run(){
		if(to != null){
			try {
				from.semaphore.acquire();
				move(from, to, this.howMany);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			chooseWay(from);
		}
	}
	
	
	private void chooseWay(Cell from) {
		ArrayList<Cell> options = findOptions(from);
		if(options.size() == 1){
			try {
				from.semaphore.acquire();
				move(from, options.get(0), from.fNum);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} else if (options.size() > 1){
			if(options.get(0).status == EXIT){
				try {
					from.semaphore.acquire();
					move(from, options.get(0), from.fNum);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				if(from.fNum > 0) {
					divide (from, from.fNum, options);
				}
			}
		} else {
			if(from.isEdge){
				try {
					from.semaphore.acquire();
					if(from.status != EXIT && maze.exitFound == false){
						from.status = EXIT;
						int party = from.fNum;
						from.semaphore.release();
						maze.exitFound = true;	
						markExitPath(from);
						printMessage(party);
					}
					else if(from.status == EXIT){
						printMessage(from.fNum);
						from.semaphore.release();
					}else{
						from.semaphore.release();
						goBack(from);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				goBack(from);
			}
		}
	}
	
	
	private void printMessage(int fNum) {
		if(fNum >= Main.startParty){
			maze.w.stop();
			System.out.println("To the pub!  " + fNum);
			maze.printMaze();
			System.out.println("  elapsed time: " + maze.w.getElapsedTime() + " ms");
		}else{
			System.out.println("Yo  " + fNum);
		}		
	}


	private void move(Cell from, Cell to, int party) {
		try{			
			to.semaphore.acquire();
			if(from.fNum > 0 && to.status != DEADEND){						
				if(from.status != EXIT){
					from.status = LIVE;}
				to.parent = from;
				to.fNum += party;
				from.fNum -= party;
				to.semaphore.release();
				from.semaphore.release();
				chooseWay(to);
			} else {
				to.semaphore.release();
				from.semaphore.release();
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	private void goBack(Cell from) {
		try{
			from.semaphore.acquire();
			if(from.fNum > 0) {
				from.status = DEADEND;
				from.semaphore.release();
				from.parent.fNum += from.fNum;
				from.fNum = 0;
			}else{
				from.semaphore.release();
			}
			if(from.parent.fNum > 0){
				if(findOptions(from.parent).size() > 0){
					chooseWay(from.parent);
				} else {
					goBack(from.parent);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	private void markExitPath(Cell cell) {
		if(cell.parent != null){
			cell.status = EXIT;
			markExitPath(cell.parent);
		}		
	}
	
	
	private ArrayList<Cell> findOptions (Cell point){
		ArrayList<Cell> options = new ArrayList<Cell>();
		for(Cell nbr : point.nbrs){
			if(!(nbr == null) && nbr.status != WALL && nbr.status != DEADEND && !nbr.equals(point.parent)){
				options.add(nbr);
			}
		}
		Collections.sort(options);
		return options;
	}
		
	
	private void divide (Cell from, int group, ArrayList<Cell> options){
		while (group > 0 && options.size() > 0){
			int party = group / options.size();
			if(party == 0){
				party = 1;
			}
			Action action = new Action(from, options.get(0), party, this.maze ); 
			Thread move = new Thread(action);
			move.start();
			options.remove(0);
			divide(from, group - party, options);
		}
	}
}
