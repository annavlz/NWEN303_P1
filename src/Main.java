import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static int startParty;
	
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
	        	 String[] row = thisLine.split("");
	        	 for(int i = 0; i < row.length; i++){
	        		 Cell cell = new Cell(row[i], count + "-" + i);
	        		 if(i == 0 || i == row.length-1 || count == 0 || count == row.length-1){
	        			 cell.isEdge = true;
	        		 }
		        	 mazeArray[count][i] = cell;
	        	 }	        	 
	        	 count++;
	         }       
	         br.close();	      
	         startParty = fNum;
	         Maze maze = new Maze(mazeArray, entryX, entryY);
	         maze.findExit();
	         
	      } catch(Exception e){
	         e.printStackTrace();
	      }
	}
}


