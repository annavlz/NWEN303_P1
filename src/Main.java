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
	         int exitX = Integer.parseInt(params[1]);
	         int exitY = Integer.parseInt(params[2]);
	         String[][] mazeArray = new String[arraySize][arraySize];
	         int count = 0;
	         while ((thisLine = br.readLine()) != null) {
	        	 String[] row = br.readLine().split("");
	        	 mazeArray[count] = row;
	        	 count++;
	         }       
	         br.close();
	         Maze maze = new Maze(mazeArray, exitX, exitY);
	         maze.findExit(maze, args[1]);
	         
	      } catch(Exception e){
	         e.printStackTrace();
	      }
	}
}


