
public class Cell {
	boolean isWall;
	boolean isEntry = false;
	Cell northN;
	Cell southN;
	Cell westN;
	Cell eastN; 
	boolean visited = false;
	boolean exitPath = false;
	Cell parent;
	int friends = 0;
	
	public Cell (String type){
		if(type == "X"){
			isWall = true;
		} else {
			isWall = false;
		}
	}
}
