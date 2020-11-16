package uk.ac.cam.gtf23.prejava.ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayLifeManualTester {

    public static void main(String args[]){
        boolean [][] world;
        List<String> input = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        int row = in.nextInt();
        in.nextLine();
        for(int i=0;i<row;i++){
           input.add(in.nextLine());
        }
        int col = input.get(0).length();

        world = new boolean[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                ArrayLife.setCell(world, j, i, (input.get(i).charAt(j) == '1'));
            }
        }

        while(true){
            int a = in.nextInt();
            int b = in.nextInt();
            in.nextLine();
            System.out.println(ArrayLife.getCell(world, b, a));
        }
    }
}
