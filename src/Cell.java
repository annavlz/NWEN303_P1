import java.util.ArrayList;

public class Cell {
	boolean isWall;
	boolean isEdge;
	boolean deadEnd;
	ArrayList<Cell> nbrs = new ArrayList <Cell>();
	boolean exitPath = false;
	Cell parent;
	int fNum = 0;
	boolean isEntry = false;
	
	public Cell (String type){
		//System.out.println(type);
		if(type == "X"){
			this.isWall = true;
		} else {
			this.isWall = false;
		}
	}
}
