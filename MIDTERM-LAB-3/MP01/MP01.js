// ============================================================
// MP01 - Load Dataset and Display Total Records
// Programmer: EYAS, SOPHIA WELYNNE S.
// Course: Programming 2 | BSIT GD - 1st Year
// Description: This program reads a CSV file, loads the
//              dataset, and displays the total number of
//              records (data rows) found in the file.
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
        return; // Stop the program if file is not found
    }

    // Read the entire file content as a single string (UTF-8 encoding)
    const fileContent = fs.readFileSync(filePath, "utf8");

    // Split the file content into individual lines using newline character
    // The filter removes any completely empty lines from the array
    const lines = fileContent.split("\n").filter(line => line.trim() !== "");

    // 'totalRecords' counts all valid data rows found after the header
    let totalRecords = 0;

    // 'headerFound' becomes true once we locate the real column header row
    let headerFound = false;

    // 'totalLines' tracks how many lines were in the file total
    const totalLines = fileContent.split("\n").length;

    // --- Process each line ---
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i].trim(); // Remove extra whitespace from the line

        // Split the line into columns using comma as the separator
        // This basic split works for this dataset's structure
        const columns = line.split(",");

        // --- Find the real header row ---
        // We look for the row whose first column is "Candidate"
        if (!headerFound && columns[0].trim().toLowerCase() === "candidate") {
            headerFound = true; // Mark that we found the header row
            continue;           // Skip counting the header itself
        }

        // --- Count only rows that come AFTER the header ---
        // Also make sure the first column is not empty (skip metadata/footer rows)
        if (headerFound && columns[0].trim() !== "") {
            totalRecords++; // This is a valid data record
        }
    }

    // --- Display the results ---
    console.log("\n============================================================");
    console.log("          MP01 - LOAD DATASET AND DISPLAY TOTAL RECORDS");
    console.log("============================================================");
    console.log("File            : " + filePath);
    console.log("Total Lines Read: " + totalLines);
    console.log("------------------------------------------------------------");
    console.log("  >> Total Records in Dataset: " + totalRecords);
    console.log("============================================================");
});