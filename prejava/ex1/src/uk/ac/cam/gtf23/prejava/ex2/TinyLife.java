package uk.ac.cam.gtf23.prejava.ex2;

public class TinyLife {

    public static final int TABLE_SIZE = 8;

    public static boolean getCell(long world, int col, int row){
        if(notInRange(col) || notInRange(row))
            return false;
        return PackedLong.get(world, row*8 + col);
    }

    private static boolean notInRange(int col) {
        return col >= TABLE_SIZE || col < 0;
    }

    public static long setCell(long world, int col, int row, boolean value){
        return PackedLong.set(world, row*8 + col, value);
    }

    public static void print(long world) {
        System.out.println("-");
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                System.out.print(getCell(world, col, row) ? "#" : "_");
            }
            System.out.println();
        }
    }

    public static int countNeighbours(long world, int col, int row){
        int neighbours = 0;
        for(int i = -1; i<=1;i++){
            for(int j = -1; j<=1;j++)
                if( getCell(world, col+i, row+j)) neighbours++;
        }
        if(getCell(world, col, row)) neighbours--;
        return neighbours;
    }

    public static boolean computeCell(long world,int col, int row){
        // liveCell is true if the cell at position (col,row) in world is live
        boolean liveCell = getCell(world, col, row);

        // neighbours is the number of live neighbours to cell (col,row)
        int neighbours = countNeighbours(world, col, row);

        // we will return this value at the end of the method to indicate whether
        // cell (col,row) should be live in the next generation
        boolean nextCell = false;

        //A live cell with less than two neighbours dies (underpopulation)
        if (neighbours < 2) {
            nextCell = false;
        }

        //A live cell with two or three neighbours lives (a balanced population)
        if((neighbours >= 2) && (neighbours <= 3) && liveCell){
            nextCell = true;
        }

        //A live cell with with more than three neighbours dies (overcrowding)
        if(neighbours > 3){
            nextCell = false;
        }

        //A dead cell with exactly three live neighbours comes alive
        if(neighbours == 3 && !liveCell){
            nextCell = true;
        }

        return nextCell;
    }

    public static long nextGeneration(long world){
        long nextWorld = world;
        for(int i = 0; i < TABLE_SIZE; i++){
            for(int j = 0; j < TABLE_SIZE; j++){
                boolean newValue = computeCell(world, i, j);
                nextWorld = setCell(nextWorld, i, j, newValue);
            }
        }
        return nextWorld;
    }

    public static void play(long world) throws java.io.IOException {
        int userResponse = 0;
        while (userResponse != 'q') {
            print(world);
            userResponse = System.in.read();
            world = nextGeneration(world);
        }
    }

    public static void main(String[] args) throws java.io.IOException {
        play(Long.decode(args[0]));
    }
}
