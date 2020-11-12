import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args){
        readInput("input.txt");
        System.out.println(mazes.size());
//        System.out.println(temp[0][0].n + "" + temp[0][0].s + temp[0][0].w + temp[0][0].e);
//        System.out.println(dfs(mazes.get(0)));
//        System.out.println(dfs(mazes.get(1)));
        System.out.println(dfs(mazes.get(2)));
//        System.out.println(dfs(mazes.get(3)));
        System.out.println(Arrays.deepToString(mazes.get(2)).replace("], ", "]\n"));



    }
    public static List<Cell[][]> mazes = new ArrayList<>();


    public static class Cell{
        public int n, s, w, e;
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

        public void closeCell(){
            this.n = 1;
            this.s = 1;
            this.w = 1;
            this.e = 1;
        }

        @Override
        public String toString() {
            return "" + n + s + w + e;
        }
    }

    public static void readInput(String fName) {
        try{
            String line;
            File in = new File(fName);
            Scanner scan = new Scanner(in);
            int numMazes = Integer.valueOf(scan.nextLine()); //first line is number of mazes
            int sizeMaze = Integer.valueOf(scan.nextLine()); //second line is num "n" for the nxn maze
            Cell[][] tempMaze = new Cell[sizeMaze][sizeMaze];
            for(int i = 0; i < numMazes; i++){
                for(int j = 0; j < sizeMaze; j++){
                    line = scan.nextLine();
                    for(int k = 0; k < line.length(); k += 4){
                        String bits = line.substring(k, k + 4);
                        String[] temp = bits.split("");
                        int n = Integer.valueOf(temp[0]);
                        int s = Integer.valueOf(temp[1]);
                        int w = Integer.valueOf(temp[2]);
                        int e = Integer.valueOf(temp[3]);
//                        System.out.println("" + n + s + w + e);
                        tempMaze[j][k / 4] = new Cell(n, s, w, e);
                    }
                }
                mazes.add(tempMaze);
                scan.nextLine();
                tempMaze = new Cell[sizeMaze][sizeMaze];

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
        //base case: Are we at the start?
        if(current == start){
            return p;
        } else {
            //not at start check prev direction
            char prevDirection = p.charAt(p.length() - 1);

            switch (prevDirection) {
                case 'S':
                    if (current.s == 0) { // another south opening
                        p += "S";
                        current.numS += 1;
                        r++;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else if(current.w == 0){ // a west opening
                        p += "W";
                        current.numW += 1;
                        c--;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else if(current.e == 0) { // an east opening
                        p += "E";
                        current.numE += 1;
                        c++;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else { // no other opening go back north
                        //close the cell since we do not want to get back into this one
                        m[r][c].closeCell();
                        p += "N";
                        current.numN += 1;
                        r--;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    }
                case 'N':
                    if (current.w == 0) { // Check west opening first since going south doesnt make sense
                        p += "W";
                        current.numW += 1;
                        c--;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else if(current.e == 0){ // a east opening
                        p += "E";
                        current.numW += 1;
                        c++;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else if(current.n == 0) { // an north opening
                        p += "N";
                        current.numN += 1;
                        r--;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else { // no other opening go back south
                        m[r][c].closeCell();
                        p += "S";
                        current.numS += 1;
                        r++;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    }
                case 'E':
                    if(current.n == 0){
                        p += "N";
                        current.numN += 1;
                        r--;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else if (current.s == 0){
                        p += "S";
                        current.numS += 1;
                        r++;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else if (current.e == 0){
                        p += "E";
                        current.numW += 1;
                        c++;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else { //no other opening go back west
                        m[r][c].closeCell();
                        p += "W";
                        c--;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    }
                case 'W':
                    if(current.n == 0){
                        p += "N";
                        current.numN += 1;
                        r--;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else if (current.s == 0){
                        p += "S";
                        current.numS += 1;
                        r++;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else if (current.w == 0) {
                        p += "W";
                        current.numW += 1;
                        c--;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    } else { // no other opening go back east
                        m[r][c].closeCell();
                        p += "E";
                        c++;
                        current = m[r][c];
                        return dfs(m, start, p, current, r, c);
                    }

            }
        }

        return p;
    }

}
