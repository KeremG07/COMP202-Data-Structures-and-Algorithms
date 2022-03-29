import java.util.Random;
import java.util.Arrays;

public class HW1 {
	
	// This is the method that returns the count of 'a' chars in the matrix
	// Feel free to add a helper method for the recursive part of the algorithm
	public static int acount(char[][] mat) {
		int result = 0;
		// Your code goes here 
    int n = mat.length;

    for(int row=0; row<n; row++) {
      int s = 0;
      int e = n-1;
      result = result + recBS(mat[row], s, e);
    }
		return result;
	}

  public static int recBS(char[] row, int s, int e) {
    int col = s + (e-s)/2;
    int n = row.length;

    if(row[col] == 'b') {
      if(col == 0) {
        return 0;
      }
      if(row[col-1] == 'a') {
        return col;
      }
      if(row[col-1] == 'b') {
        e = col - 1;
        return recBS(row, s, e);
      }
    }

    if(row[col] == 'a') {
      if(col == n-1) return n;
      s = col + 1;
      return recBS(row, s, e);
    }

    return 99;
  }

	public static void main(String[] args) {
		// This method creates a test case for you
		int n = 5;
		Random rand = new Random();
		char[][] input = new char[n][n];
		for (int i = 0; i < n; i++) {
			int a_num = rand.nextInt(n);
			for (int j = 0; j < a_num; j++) {
				input[i][j] = 'a';
			}
			for (int j = a_num; j < n; j++) {
				input[i][j] = 'b';
			}
		}
		// Here you can see the matrix row by row 
		System.out.println(Arrays.deepToString(input));
		// Here you can see the result of your function
		System.out.println(acount(input));
	}

}
