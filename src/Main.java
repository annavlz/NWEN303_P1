import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {
	public static void main(String[] args) {
		String  thisLine = null;
      try{
	         BufferedReader br = new BufferedReader(new FileReader(args[0]));
	         String[] params = br.readLine().split("\\s"); //getParams
	         int arraySize = Integer.parseInt(params[0]);
	         int entryX = Integer.parseInt(params[1]);
	         int entryY = Integer.parseInt(params[2]);
	         int fNum = Integer.parseInt(args[1]);
	         Cell[][] mazeArray = new Cell[arraySize][arraySize];
	         int count = 0;
	         while ((thisLine = br.readLine()) != null) {
	        	 String[] row = br.readLine().split("");
	        	 for(int i = 0; i < row.length; i++){
	        		 Cell cell = new Cell(row[i]);
	        		 if(i == 0 || i == 9 || count == 0 || count == 9){
	        			 cell.isEdge = true;
	        		 }
		        	 mazeArray[count][i] = cell;
	        	 }	        	 
	        	 count++;
	         }       
	         br.close();	         
	         Maze maze = new Maze(mazeArray, entryX, entryY);
	         maze.findExit(maze, fNum);
	         
	      } catch(Exception e){
	         e.printStackTrace();
	      }
	}
}


