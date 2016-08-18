import java.io.FileReader;
import java.io.IOException;


public class Main {
		public static void main(String[] args) {		
			try {
				Maze maze = new Maze(new FileReader(args[0]));			
			} catch(IOException e) {			
				System.err.println("Error loading file: " + args[0]);
				System.err.println(e.getMessage());			
			}
		}
}
