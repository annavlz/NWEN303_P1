import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Cell {
	boolean isWall;
	boolean isEdge;
	boolean deadEnd;
	ArrayList<Cell> nbrs = new ArrayList <Cell>();
	boolean exitPath = false;
	Cell parent;
	int fNum = 0;
	boolean isEntry = false;
	String name;
//	Collection<Cell> visited = new HashSet<Cell>();
	
	public Cell (String type, String name){
		if(type.equals("X")){
			this.isWall = true;
		} else {
			this.isWall = false;
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
		String wall = this.isWall ? " wall," : "";
		String exitPath = this.exitPath ? " exitPath," : "";
		String deadEnd = this.deadEnd ? " deadEnd," : "";
		String entry = this.isEntry ? " entry," : "";
		int friends = this.fNum;
//		String parent = "";
//		if(this.parent.name != null) { 
//			parent = "parent " + this.parent.name;
//		} else { }
		return name + wall + entry + exitPath + deadEnd + "  " + friends + "    ";
	}
}
