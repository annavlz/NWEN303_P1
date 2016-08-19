import java.util.ArrayList;

public class Cell {
	boolean isWall;
	boolean isEdge;
	boolean deadEnd;
	ArrayList<Cell> nbrs = new ArrayList <Cell>();
	boolean exitPath = false;
	Cell parent;
	int fNum = 0;
	boolean entry = false;
	
	public Cell (String type){
		if(type == "X"){
			isWall = true;
		} else {
			isWall = false;
		}
	}
}
