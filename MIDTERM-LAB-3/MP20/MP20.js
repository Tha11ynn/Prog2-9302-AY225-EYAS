// ============================================================
// MP20 - Convert CSV Dataset to JSON
// Programmer: EYAS, SOPHIA WELYNNE S.
// Course: Programming 2 | BSIT GD - 1st Year
// Description: This program reads a CSV file, parses each
//              data row using the header columns as keys,
//              and outputs the entire dataset as a formatted
//              JSON array saved to a new .json file.
// ============================================================

// Import the built-in Node.js modules needed for this program
const fs = require("fs");         // 'fs' is used to read and write files
const readline = require("readline"); // 'readline' is used to get input from the user
const path = require("path");     // 'path' helps us build the output file path cleanly

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

    // Build the output JSON file path:
    // Replace the .csv extension with .json, or append _output.json if no .csv
    const ext = path.extname(filePath).toLowerCase();
    const jsonFilePath = ext === ".csv"
        ? filePath.slice(0, -4) + ".json"
        : filePath + "_output.json";

    // Read the entire file content as a single string (UTF-8 encoding)
    const fileContent = fs.readFileSync(filePath, "utf8");

    // Split the file content into individual lines
    // The filter removes any completely empty lines from the array
    const lines = fileContent.split("\n").filter(line => line.trim() !== "");

    // 'headerColumns' will store the column names from the header row
    let headerColumns = null;

    // 'jsonArray' will hold each converted row as a JavaScript object
    let jsonArray = [];

    // 'dataRowCount' counts how many records were successfully converted
    let dataRowCount = 0;

    // --- Process each line of the CSV ---
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i].trim(); // Remove extra whitespace from the line

        // Split the current line into individual column values by comma
        const columns = line.split(",");

        // --- Find the real header row ---
        // We identify it by checking if the first column value is "Candidate"
        if (headerColumns === null && columns[0].trim().toLowerCase() === "candidate") {
            headerColumns = columns.map(col => col.trim()); // Save cleaned header names
            continue; // Move to the next line without processing this one
        }

        // --- Convert data rows that come AFTER the header ---
        // Skip rows where the first column is empty (metadata or footer rows)
        if (headerColumns !== null && columns[0].trim() !== "") {
            dataRowCount++;

            // Create a new empty JavaScript object for this row
            const record = {};

            // Loop through each named column and map the value to the key
            headerColumns.forEach((colName, j) => {

                // Only include columns that have a real, non-empty header name
                if (colName !== "") {
                    // Get the value for this column, or empty string if missing
                    const value = (j < columns.length) ? columns[j].trim() : "";
                    record[colName] = value; // Assign: { "ColumnName": "value" }
                }
            });

            jsonArray.push(record); // Add this row's object to the array
        }
    }

    // --- Write the JSON array to the output file ---
    // JSON.stringify converts the array to a formatted JSON string
    // The '2' argument adds 2-space indentation for readability
    const jsonOutput = JSON.stringify(jsonArray, null, 2);
    fs.writeFileSync(jsonFilePath, jsonOutput, "utf8");

    // --- Display the results summary ---
    console.log("\n============================================================");
    console.log("           MP20 - CONVERT CSV DATASET TO JSON");
    console.log("============================================================");
    console.log("Input CSV File  : " + filePath);
    console.log("Output JSON File: " + jsonFilePath);
    console.log("------------------------------------------------------------");
    console.log("  >> Total Records Converted: " + dataRowCount);
    console.log("  >> JSON file successfully created!");
    console.log("============================================================");
});