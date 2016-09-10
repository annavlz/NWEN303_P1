import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;

public class Cell implements Comparable<Cell> {
	final int EXIT = 0;
	final int NEW = 1;
	final int LIVE = 2;
	final int DEADEND = 3;
	final int WALL = 4;
	boolean isEdge;
	int status;
	ArrayList<Cell> nbrs = new ArrayList <Cell>();
	Cell parent;
	int fNum = 0;
	boolean isEntry = false;
	String name;
	Lock lock = new ReentrantLock();
	Semaphore semaphore = new Semaphore(1);

	
	public Cell (String type, String name){
		if(type.equals("X")){
			this.status = WALL;
		} else {
			this.status = NEW;
		}
		this.name = name;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Cell))
			return false;
		Cell other = (Cell) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	@Override
	public String toString(){
		String name = "Name: " + this.name;
		String wall = this.status == WALL ? " wall," : "";
		String exitPath = this.status == EXIT ? " exitPath," : "";
		String deadEnd = this.status == DEADEND ? " deadEnd," : "";
		String entry = this.isEntry ? " entry," : "";
		int friends = this.fNum;
		return name + wall + entry + exitPath + deadEnd ;
	}

	
	@Override
	public int compareTo(Cell other) {
		return Integer.compare(this.status, other.status);
	}	
}
