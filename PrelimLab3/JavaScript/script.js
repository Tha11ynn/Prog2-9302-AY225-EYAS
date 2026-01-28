// Apply attendance input restriction
const attendanceFields = ["day1", "day2", "day3", "day4", "day5"];
attendanceFields.forEach(id => {
    const field = document.getElementById(id);
    field.addEventListener("input", (e) => {
        // Convert input to uppercase
        let val = e.target.value.toUpperCase();
        // Keep only P, A, or E, max length 1
        if (!["P", "A", "E"].includes(val)) val = "";
        e.target.value = val;
    });
});

function calculate() {
    // Get attendance inputs
    let days = [
        document.getElementById("day1").value.trim(),
        document.getElementById("day2").value.trim(),
        document.getElementById("day3").value.trim(),
        document.getElementById("day4").value.trim(),
        document.getElementById("day5").value.trim()
    ];

    // Get grade inputs
    let lab1 = parseFloat(document.getElementById("lab1").value);
    let lab2 = parseFloat(document.getElementById("lab2").value);
    let lab3 = parseFloat(document.getElementById("lab3").value);
    let exam = parseFloat(document.getElementById("exam").value);

    // Validate grade inputs
    if (isNaN(lab1) || isNaN(lab2) || isNaN(lab3) || isNaN(exam)) {
        alert("Please complete all grade fields with valid numbers.");
        return;
    }

    // Validate attendance inputs
    for (let i = 0; i < days.length; i++) {
        if (!["P", "A", "E"].includes(days[i].toUpperCase())) {
            alert("Please complete all attendance fields with P, A, or E.");
            return;
        }
    }

    // Count absences
    let absentCount = 0;
    for (let day of days) {
        if (day.toUpperCase() === "A") absentCount++;
    }

    // Auto-fail: more than 3 absences
    if (absentCount > 3) {
        document.getElementById("prelim").textContent = "FAILED";
        document.getElementById("status").textContent = "FAILED (Too many absences)";
        document.getElementById("status").className = "failed";

        document.getElementById("reqPass").textContent = "--";
        document.getElementById("remarkPass").textContent = "IMPOSSIBLE";
        document.getElementById("remarkPass").className = "impossible";

        document.getElementById("reqExcellent").textContent = "--";
        document.getElementById("remarkExcellent").textContent = "IMPOSSIBLE";
        document.getElementById("remarkExcellent").className = "impossible";
        return;
    }

    // Calculate attendance score
    let attendanceScore = 100 - (absentCount * 20);

    // Computations
    let labAvg = (lab1 + lab2 + lab3) / 3;
    let classStanding = (attendanceScore * 0.40) + (labAvg * 0.60);
    let prelimGrade = (exam * 0.70) + (classStanding * 0.30);

    document.getElementById("prelim").textContent = prelimGrade.toFixed(2);

    let status = document.getElementById("status");
    status.textContent = prelimGrade >= 75 ? "PASSED" : "FAILED";
    status.className = prelimGrade >= 75 ? "passed" : "failed";

    // PASSING (75)
    let reqPass = (75 - (classStanding * 0.30)) / 0.70;
    if (prelimGrade >= 75) {
        document.getElementById("reqPass").textContent = exam.toFixed(2);
        document.getElementById("remarkPass").textContent = "Requirements met";
        document.getElementById("remarkPass").className = "met";
    } else if (reqPass > 100 || reqPass < 0) {
        document.getElementById("reqPass").textContent = "--";
        document.getElementById("remarkPass").textContent = "IMPOSSIBLE";
        document.getElementById("remarkPass").className = "impossible";
    } else {
        document.getElementById("reqPass").textContent = reqPass.toFixed(2);
        document.getElementById("remarkPass").textContent = "Still needed";
        document.getElementById("remarkPass").className = "pending";
    }

    // EXCELLENT (100)
    let reqExcellent = (100 - (classStanding * 0.30)) / 0.70;
    if (prelimGrade >= 100) {
        document.getElementById("reqExcellent").textContent = exam.toFixed(2);
        document.getElementById("remarkExcellent").textContent = "Requirements met";
        document.getElementById("remarkExcellent").className = "met";
    } else if (reqExcellent > 100 || reqExcellent < 0) {
        document.getElementById("reqExcellent").textContent = "--";
        document.getElementById("remarkExcellent").textContent = "IMPOSSIBLE";
        document.getElementById("remarkExcellent").className = "impossible";
    } else {
        document.getElementById("reqExcellent").textContent = reqExcellent.toFixed(2);
        document.getElementById("remarkExcellent").textContent = "Still needed";
        document.getElementById("remarkExcellent").className = "pending";
    }
}
