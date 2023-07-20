
import java.util.Random;

/**
 *
 * @author elif
 */
public class Environment {

    int size;
    int[][] matrix;

    public Environment(){
        
    }
    
    public Environment(int size) {
        this.size = size;
        matrix = new int[size][size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                matrix[i][j] = (int) Math.round(Math.random());
            }
        }
    }

    public void printMatrix(int location[]) {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (location[0] == i && location[1] == j) {
                    System.out.print("X ");
                } else {
                    System.out.print(this.matrix[i][j] + " ");
                }
            }
            System.out.println("");
        }
        System.out.println("Agent has the location " + location[0] + "-" + location[1]);
    }

    public int numberofDirtyPlaces() {
        int ones = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.matrix[i][j] == 1) {
                    ones++;
                }
            }
        }
        return ones;
    }

    public boolean checkClean() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
