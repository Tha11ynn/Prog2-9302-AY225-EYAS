/**
 * ===================================================
 * 3x3 MATRIX DETERMINANT SOLVER
 * Student: EYAS, SOPHIA WELYNNE S.
 * Course: BSCSIT 1203L 9302-AY225 (Programming 2 - Lab)
 * Assignment: Midterm Lab 2
 * Date: 04/08/26
 *
 * Description:
 * Computes determinant of a 3x3 matrix using cofactor expansion
 * and prints a detailed step-by-step solution similar to Java version.
 * ===================================================
 */

// Function to print matrix
function printMatrix(m) {
    m.forEach(row => {
        console.log(`  | ${row[0]}   ${row[1]}   ${row[2]}  |`);
    });
}

// Function to compute 2x2 determinant
function computeMinor(a, b, c, d) {
    return (a * d) - (b * c);
}

// Function to solve determinant
function solveDeterminant(m) {

    console.log("\nExpanding along Row 1 (cofactor expansion):\n");

    // Minor M11
    let m11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
    console.log(`Step 1 — Minor M11: det([${m[1][1]},${m[1][2]}],[${m[2][1]},${m[2][2]}]) = (${m[1][1]}×${m[2][2]}) - (${m[1][2]}×${m[2][1]}) = ${m[1][1]*m[2][2]} - ${m[1][2]*m[2][1]} = ${m11}`);

    // Minor M12
    let m12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
    console.log(`Step 2 — Minor M12: det([${m[1][0]},${m[1][2]}],[${m[2][0]},${m[2][2]}]) = (${m[1][0]}×${m[2][2]}) - (${m[1][2]}×${m[2][0]}) = ${m[1][0]*m[2][2]} - ${m[1][2]*m[2][0]} = ${m12}`);

    // Minor M13
    let m13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);
    console.log(`Step 3 — Minor M13: det([${m[1][0]},${m[1][1]}],[${m[2][0]},${m[2][1]}]) = (${m[1][0]}×${m[2][1]}) - (${m[1][1]}×${m[2][0]}) = ${m[1][0]*m[2][1]} - ${m[1][1]*m[2][0]} = ${m13}`);

    // Cofactors
    let c11 = (+1) * m[0][0] * m11;
    let c12 = (-1) * m[0][1] * m12;
    let c13 = (+1) * m[0][2] * m13;

    console.log("\nCofactors:");
    console.log(`C11 = (+1) × ${m[0][0]} × ${m11} = ${c11}`);
    console.log(`C12 = (-1) × ${m[0][1]} × ${m12} = ${c12}`);
    console.log(`C13 = (+1) × ${m[0][2]} × ${m13} = ${c13}`);

    let det = c11 + c12 + c13;

    console.log(`\ndet(M) = ${c11} + (${c12}) + ${c13}`);

    return det;
}

// Hardcoded matrix
const matrix = [
    [3, 2, 4],
    [1, 5, 2],
    [6, 3, 1]
];

// Output header
console.log("===================================================");
console.log("  3x3 MATRIX DETERMINANT SOLVER");
console.log("  Student: EYAS, SOPHIA WELYNNE S.");
console.log("  Assigned Matrix:");
console.log("===================================================");

// Print matrix
printMatrix(matrix);

// Solve determinant
let det = solveDeterminant(matrix);

// Final output
console.log("\n===================================================");
console.log(`  ✓  DETERMINANT = ${det}`);

if (det === 0) {
    console.log("  The matrix is SINGULAR — it has no inverse.");
}

console.log("===================================================");
