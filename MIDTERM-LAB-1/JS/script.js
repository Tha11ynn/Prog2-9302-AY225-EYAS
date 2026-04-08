// Programmer: Sophia Welynne S. Eyas - 23-0118-315
// Task: Top 10 Customers Report (Node.js Terminal)

const fs = require('fs');
const readline = require('readline');

// Create readline interface
const rl = readline.createInterface({
input: process.stdin,
output: process.stdout
});

// Ask file path
function askFilePath() {
rl.question("Enter dataset file path: ", function(path) {
    if (fs.existsSync(path)) {
    processCSV(path);
    } else {
    console.log("Invalid file path. Try again.");
    askFilePath();
    }
});
}

// Read CSV and process
function processCSV(path) {
try {
    const data = fs.readFileSync(path, 'utf-8');
    const lines = data.trim().split('\n');

    const salesMap = {};

    // Skip header
    for (let i = 1; i < lines.length; i++) {
    const parts = lines[i].split(',');
    if (parts.length < 2) continue;

    const customer = parts[0].trim();
    const sale = parseFloat(parts[1].trim());
    if (isNaN(sale)) continue;

    if (salesMap[customer]) {
        salesMap[customer] += sale;
    } else {
        salesMap[customer] = sale;
    }
    }

    // Sort descending
    const sorted = Object.entries(salesMap).sort((a, b) => b[1] - a[1]);

    // Display top 10
    console.log("\n=== Top 10 Customers by Revenue ===");
    console.log("No.  Customer Name             Total Sales");
    console.log("-----------------------------------------");

    for (let i = 0; i < Math.min(10, sorted.length); i++) {
    const [customer, total] = sorted[i];
    console.log(`${i + 1}.   ${customer.padEnd(25)} $${total.toFixed(2)}`);
    }

} catch (err) {
    console.log("Error reading file:", err.message);
} finally {
    rl.close();
}
}

askFilePath();
