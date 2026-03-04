function processFile() {

    const fileInput = document.getElementById("fileInput");
    const errorMessage = document.getElementById("errorMessage");
    const tableBody = document.querySelector("#resultTable tbody");

    errorMessage.textContent = "";
    tableBody.innerHTML = "";

    if (!fileInput.files.length) {
        errorMessage.textContent = "❌ Please upload a CSV file.";
        return;
    }

    const file = fileInput.files[0];

    if (!file.name.toLowerCase().endsWith(".csv")) {
        errorMessage.textContent = "❌ Invalid file format. Please upload a CSV file.";
        return;
    }

    const reader = new FileReader();

    reader.onload = function (event) {

        try {
            const text = event.target.result;
            const lines = text.split("\n");
            const customerSales = {};

            for (let i = 1; i < lines.length; i++) {

                const row = lines[i].split(",");

                if (row.length < 8) continue;

                const customer = row[4].trim(); // publisher
                const sales = parseFloat(row[7]); // total_sales

                if (!customer || isNaN(sales)) continue;

                customerSales[customer] = (customerSales[customer] || 0) + sales;
            }

            const sorted = Object.entries(customerSales)
                .sort((a, b) => b[1] - a[1])
                .slice(0, 10);

            displayResults(sorted);

        } catch (error) {
            errorMessage.textContent = "❌ Error processing file.";
        }
    };

    reader.readAsText(file);
}

function displayResults(records) {
    const tableBody = document.querySelector("#resultTable tbody");

    records.forEach((record, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${record[0]}</td>
            <td>${record[1].toFixed(2)}</td>
        `;
        tableBody.appendChild(row);
    });
}
