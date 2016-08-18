import java.util.ArrayList;

public class Cell {
	boolean isWall;
	boolean isEdge;
	boolean deadEnd;
	ArrayList<Cell> nbrs = new ArrayList <Cell>();
	boolean visited = false;
	boolean exitPath = false;
	Cell parent;
	int fNum = 0;
	
	public Cell (String type){
		if(type == "X"){
			isWall = true;
		} else {
			isWall = false;
		}
	}
}
