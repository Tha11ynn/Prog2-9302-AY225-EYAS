/**
 * ===================================================
 * 3x3 MATRIX DETERMINANT SOLVER
 * Student: EYAS, SOPHIA WELYNNE S.
 * Course: BSCSIT 1203L 9302-AY225 (Programming 2 - Lab)
 * Assignment: Midterm Lab 2
 * Date: 04/08/26
 *
 * Description:
 * This program computes the determinant of a 3x3 matrix
 * using cofactor expansion along the first row and
 * prints a step-by-step solution.
 * ===================================================
 */

public class DeterminantSolution {

    // Function to print the matrix neatly
    public static void printMatrix(int[][] m) {
        for (int i = 0; i < 3; i++) {
            System.out.printf("  | %2d %2d %2d |\n", m[i][0], m[i][1], m[i][2]);
        }
    }

    // Function to compute 2x2 determinant (minor)
    public static int computeMinor(int a, int b, int c, int d) {
        return (a * d) - (b * c);
    }

    // Function to solve determinant using cofactor expansion
    public static int solveDeterminant(int[][] m) {

        System.out.println("\nExpanding along Row 1:\n");

        // Step 1: Minor M11
        int m11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
        System.out.println("Step 1 - Minor M11: det([5,2],[3,1]) = (5*1) - (2*3) = " + m11);

        // Step 2: Minor M12
        int m12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
        System.out.println("Step 2 - Minor M12: det([1,2],[6,1]) = (1*1) - (2*6) = " + m12);

        // Step 3: Minor M13
        int m13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);
        System.out.println("Step 3 - Minor M13: det([1,5],[6,3]) = (1*3) - (5*6) = " + m13);

        // Cofactors
        int c11 = (+1) * m[0][0] * m11;
        int c12 = (-1) * m[0][1] * m12;
        int c13 = (+1) * m[0][2] * m13;

        System.out.println("\nCofactors:");
        System.out.println("C11 = (+1) * 3 * " + m11 + " = " + c11);
        System.out.println("C12 = (-1) * 2 * " + m12 + " = " + c12);
        System.out.println("C13 = (+1) * 4 * " + m13 + " = " + c13);

        int det = c11 + c12 + c13;

        System.out.println("\ndet(M) = " + c11 + " + (" + c12 + ") + " + c13);

        return det;
    }

    public static void main(String[] args) {

        // Hardcoded matrix
        int[][] matrix = {
            {3, 2, 4},
            {1, 5, 2},
            {6, 3, 1}
        };

        System.out.println("===================================================");
        System.out.println("  3x3 MATRIX DETERMINANT SOLVER");
        System.out.println("  Student: EYAS, SOPHIA WELYNNE S.");
        System.out.println("===================================================");

        printMatrix(matrix);

        int det = solveDeterminant(matrix);

        System.out.println("\n===================================================");
        System.out.println("  DETERMINANT = " + det);

        if (det == 0) {
            System.out.println("  The matrix is SINGULAR - it has no inverse.");
        }

        System.out.println("===================================================");
    }
}
