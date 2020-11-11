import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args){
        readInput("input.txt");



    }
    public static List<Cell[][]> mazes = new ArrayList<>();


    public static class Cell{
        public int n, s, w, e;
        public int timesExplored;
        public int numN, numS, numW, numE;

        public Cell(int n, int s, int w, int e){
            this.n = n;
            this.s = s;
            this.w = w;
            this.e = e;
            numN = 0;
            numS = 0;
            numW = 0;
            numE = 0;
        }


    }

    public static void readInput(String fName) {
        try{
            int numMazes;
            int sizeMaze;
            String line;
            Cell[][] tempMaze;
            File in = new File(fName);
            Scanner scan = new Scanner(in);
            numMazes = Integer.valueOf(scan.nextLine()); //first line is number of mazes
            sizeMaze = Integer.valueOf(scan.nextLine()); //second line is num "n" for the nxn maze

            for(int i = 0; i < numMazes; i++){
                tempMaze = new Cell[sizeMaze][sizeMaze];
                for(int j = 0; j < sizeMaze; j++){
                    line = scan.nextLine();
                    for(int k = 0; k < line.length(); k += 4){
                        String bits = line.substring(k, k + 4);
                        String[] temp = bits.split("");
                        int n = Integer.valueOf(temp[0]);
                        int s = Integer.valueOf(temp[1]);
                        int w = Integer.valueOf(temp[2]);
                        int e = Integer.valueOf(temp[3]);
                        tempMaze[i][j / 4] = new Cell(n, s, e, w);
                    }
                }
                mazes.add(tempMaze);
                scan.nextLine();
            }
            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String dfs(Cell[][] maze){
        String path = "";
        int row = 0;
        int col = 0;
        Cell start = maze[row][col];
        Cell current = start;
        //first move is either E or S
        if(current.n == 1 && current.s == 1 && current.w == 1){
            path += "E";
            current.numE += 1;
            col++;
            current = maze[row][col];
        } else if(current.n == 1 && current.e == 1 && current.w == 1){
            path += "S";
            current.numS += 1;
            row++;
            current = maze[row][col];
        }

        return dfs(maze, start, path, current, row, col);
    }

    private static String dfs(Cell[][] m, Cell start, String p, Cell current, int r, int c){
        //base case: Are we already at the start?
        if(current == start){
            return p;
        }

        return p;
    }

}
