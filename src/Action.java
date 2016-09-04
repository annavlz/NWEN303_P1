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
	
	public Action(Cell from){
		this.from = from;
	}
	
	public Action(Cell from, Cell to, int howMany){		
		this.from = from;
		this.to = to;
		this.howMany = howMany;
	}
	
	public void run(){
		if(to != null){
			move(from, to, this.howMany);
		}else{
			chooseWay(from);
		}

	}
	
	private void chooseWay(Cell from) {
		ArrayList<Cell> options = findOptions(from);
		if(options.size() == 1){
			move(from, options.get(0), from.fNum);
		} else if (options.size() > 1){
			if(options.get(0).status == EXIT){
				move(from, options.get(0), from.fNum);
			}else{
				divide (from, from.fNum, options);
			}
		} else {
//			System.out.println("FF");
			if(from.isEdge && from.status != EXIT){
				from.lock.lock();
				try {
					System.out.println("Yo  " + from.fNum);
					markExitPath(from);
				} finally {
					from.lock.unlock();
				}
				if(from.fNum >= 8){
					System.out.println("To the pub!  " + from.fNum);
				}
			} else if(from.isEdge && from.fNum == Main.startParty) { 
				System.out.println("To the pub!  " + from.fNum);
			} else{
				goBack(from);
			}
		}
	}
	
	private void move(Cell from, Cell to, int party) {
		
		System.out.println(from + "     " + to + "     " + Thread.currentThread().getName() +"   "+ party + "  " +from.fNum + "  " +to.fNum);
		
		if(from.lock.tryLock()){
			if(to.lock.tryLock()){ 
				try{
					if(from.fNum > 0 && to.status != DEADEND){
			
						if(from.status != EXIT){
							from.status = LIVE;
						}
						to.parent = from;
						to.fNum += party;
						from.fNum -= party;
						chooseWay(to);
					}			
				}finally{
					from.lock.unlock();
					to.lock.unlock();
				}
			}else{
				from.lock.unlock();
			}
		}
	}
	
	
	private void goBack(Cell from) {
		from.lock.lock();
		if(from.parent.lock.tryLock()){ 
			try{
				if(from.fNum > 0) {
	
					from.status = DEADEND;
					from.parent.fNum += from.fNum;
					from.fNum = 0;
				}
				if(from.parent.fNum > 0){
					if(findOptions(from.parent).size() > 0){
						chooseWay(from.parent);
					} else {
						goBack(from.parent);
					}
				}
			}finally{
				from.lock.unlock();
				from.parent.lock.unlock();
			}
		}else{
			from.lock.unlock();
		}
	}
	
	
	private void markExitPath(Cell cell) {
//		System.out.println("Exit path " + cell);
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
			Action action = new Action(from, options.get(0), party ); 
			Thread move = new Thread(action);
			move.start();
			options.remove(0);
			divide(from, group - party, options);
		}
	}
	

}
