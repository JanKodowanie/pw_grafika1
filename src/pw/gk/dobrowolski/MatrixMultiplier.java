package pw.gk.dobrowolski;

public class MatrixMultiplier {
    public static float[][] multiplyMatrices(float[][] A, float [][] B) {
        int rowsA = A.length;
        int rowsB = B.length;

        if (rowsA < 1)
            throw new RuntimeException("Invalid dimensions of A");
        if (rowsB < 1)
            throw new RuntimeException("Invalid dimensions of B");

        int colsA = A[0].length;
        int colsB = B[0].length;

        if (colsA != rowsB)
            throw new RuntimeException("A has to have as many cols as B has rows.");

        float[][] result = new float[rowsA][colsB];

        for (int i=0; i<rowsA; i++) {
            for (int j=0; j<colsB; j++) {
                float sum = 0;
                for (int k=0; k<colsA; k++) {
                    sum += A[i][k] * B[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }
}
