// ============================================================
// MP02 - Display First 10 Rows of Dataset
// Programmer: EYAS, SOPHIA WELYNNE S.
// Course: Programming 2 | BSIT GD - 1st Year
// Description: This program reads a CSV file and displays
//              the first 10 data rows in a readable format,
//              along with the column headers of the dataset.
// ============================================================

// Import the built-in Node.js modules needed for this program
const fs = require("fs");         // 'fs' is used to read files from disk
const readline = require("readline"); // 'readline' is used to get input from the user

// Create an interface to read input from the keyboard (stdin)
// and output to the screen (stdout)
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

// Ask the user to enter the path to the CSV file
rl.question("Enter the CSV dataset file path: ", (filePath) => {

    // Close the readline interface after getting the input
    rl.close();

    // --- Read the file ---
    // Check first if the file actually exists at the given path
    if (!fs.existsSync(filePath)) {
        console.log("ERROR: File not found. Please check the file path.");
        return; // Stop the program if the file is not found
    }

    // Read the entire file content as a single string (UTF-8 encoding)
    const fileContent = fs.readFileSync(filePath, "utf8");

    // Split the file content into individual lines
    // The filter removes any completely empty lines from the array
    const lines = fileContent.split("\n").filter(line => line.trim() !== "");

    // 'headerColumns' will store the column names from the header row
    let headerColumns = null;

    // 'first10Rows' will hold up to 10 parsed data rows (each row is an array)
    let first10Rows = [];

    // --- Process each line to find header and collect first 10 data rows ---
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i].trim(); // Remove extra whitespace

        // Split the current line into individual column values
        const columns = line.split(",");

        // --- Find the real header row ---
        // We identify it by checking if the first column is "Candidate"
        if (headerColumns === null && columns[0].trim().toLowerCase() === "candidate") {
            headerColumns = columns.map(col => col.trim()); // Save cleaned header names
            continue; // Move to the next line
        }

        // --- Collect data rows that come AFTER the header ---
        // Skip rows where the first column is empty (metadata or footer rows)
        if (headerColumns !== null && columns[0].trim() !== "") {
            first10Rows.push(columns); // Add this row to our collection

            // Stop once we have collected 10 data rows
            if (first10Rows.length === 10) break;
        }
    }

    // --- Display the results ---
    console.log("\n============================================================");
    console.log("           MP02 - FIRST 10 ROWS OF DATASET");
    console.log("============================================================");
    console.log("File: " + filePath);
    console.log("------------------------------------------------------------");

    // If no header was found, the file format may be unexpected
    if (headerColumns === null) {
        console.log("  ERROR: Could not find the dataset header row.");
    } else {
        // Loop through each of the first 10 rows and display them
        first10Rows.forEach((row, index) => {
            console.log(`\n  Record #${index + 1}`);
            console.log("  ---------------------------");

            // Print each field with its column name as a label
            headerColumns.forEach((colName, j) => {

                // Only display columns that have a real header name
                if (colName !== "") {
                    // Get the value safely; show "(empty)" if the field is missing
                    let value = (j < row.length) ? row[j].trim() : "(empty)";
                    if (value === "") value = "(empty)";

                    // Pad the column name for neat alignment
                    console.log(`  ${colName.padEnd(12)} : ${value}`);
                }
            });
        });

        console.log("\n------------------------------------------------------------");
        console.log("  Rows Displayed: " + first10Rows.length);
    }

    console.log("============================================================");
});