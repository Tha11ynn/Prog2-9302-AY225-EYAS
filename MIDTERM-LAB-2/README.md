# Programming 2 Lab work 02

## 👤 Student Information
**Name:** EYAS, SOPHIA WELYNNE S.  
**Course:** BSCSIT 1203L 9302-AY225 (Programming 2 - Lab)
**School:** University of Perpetual Help System DALTA – Molino Campus  

---

## 📌 Assignment Title
3×3 Matrix Determinant Solver using Cofactor Expansion

---

## 🧮 Assigned Matrix
| 3 2 4 |
| 1 5 2 |
| 6 3 1 |


---

## 💻 Program Description

This project implements a 3×3 matrix determinant solver using **cofactor expansion along the first row**.  

Two versions were created:
- Java (object-oriented approach)
- JavaScript using Node.js (scripting approach)

Both programs:
- Display the matrix
- Show step-by-step minor calculations
- Compute cofactors
- Output the final determinant

---

## ▶️ How to Run the Programs

### ☕ Java Version

1.Compile the Java file
2. java DeterminantSolver

### ☕ Java Version
1. Open terminal / command prompt
2. Run node DeterminantSolver.js


Output:
===================================================
  3x3 MATRIX DETERMINANT SOLVER
  Student: EYAS, SOPHIA WELYNNE S.
  Assigned Matrix:
===================================================
  | 3   2   4  |
  | 1   5   2  |
  | 6   3   1  |

Expanding along Row 1 (cofactor expansion):

Step 1 — Minor M11: det([5,2],[3,1]) = (5×1) - (2×3) = 5 - 6 = -1
Step 2 — Minor M12: det([1,2],[6,1]) = (1×1) - (2×6) = 1 - 12 = -11
Step 3 — Minor M13: det([1,5],[6,3]) = (1×3) - (5×6) = 3 - 30 = -27

Cofactors:
C11 = (+1) × 3 × -1 = -3
C12 = (-1) × 2 × -11 = 22
C13 = (+1) × 4 × -27 = -108

det(M) = -3 + (22) + -108

===================================================
  ✓  DETERMINANT = -89
===================================================


