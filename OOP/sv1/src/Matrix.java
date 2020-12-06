import java.util.ArrayList;
import java.util.List;

public class Matrix {
    public static List<List<Float>> createIdentity(int n){
        List<List<Float>> matrix = new ArrayList<>();
        for(int i =0;i<n;i++){
            List<Float> row = new ArrayList<>();
            for(int j=0;j<n;j++){
                if(i==j){
                    row.add(1f);
                }else{
                    row.add(0f);
                }
            }
            matrix.add(row);
        }
        return matrix;
    }

    public static <T> void transpose(ArrayList<ArrayList<T>> matrix){
        for(int i=0;i<matrix.size(); i++){
            for(int j=0;j <i;j++){
                T ij = matrix.get(i).get(j);
                T ji = matrix.get(j).get(i);
                matrix.get(i).set(j, ji);
                matrix.get(j).set(j, ij);
            }
        }
    }
}
