//Predefined credentials
const validUsername = "adm1n";
const validPassword = "passw0rd";

//Beep sound for wrong password
const beep = new Audio("beepbeep.mp3");

//Attendance records array 
let attendanceRecords = [];

//Handle login form submission 
document.getElementById("loginForm").addEventListener("submit", function (e) {
e.preventDefault();

const username = document.getElementById("username").value.trim();
const password = document.getElementById("password").value.trim();
const message = document.getElementById("message");
const timestampDiv = document.getElementById("timestamp");

  //Validate credentials 
if (username === validUsername && password === validPassword) {
    const now = new Date();
    const formattedTime = now.toLocaleString();

    //Record attendance 
    attendanceRecords.push({ username, timestamp: formattedTime });

    message.style.color = "green";
    message.textContent = `✅ Attendance Recorded: Welcome, ${username}!`;
    timestampDiv.textContent = `Timestamp: ${formattedTime}`;

    //Update table view 
    renderAttendanceTable();

    //Show download button 
    document.getElementById("downloadBtn").style.display = "block";
} else {
    beep.play();
    message.style.color = "red";
    message.textContent = "❌ Invalid credentials. Please try again.";
    timestampDiv.textContent = "";
}

  //Clear password field
document.getElementById("password").value = "";
});

// Render attendance records into table 
function renderAttendanceTable() {
const tableBody = document.getElementById("attendanceBody");
tableBody.innerHTML = ""; // clear old rows

attendanceRecords.forEach((record, index) => {
const row = document.createElement("tr");
row.innerHTML = `
    <td>${index + 1}</td>
    <td>${record.username}</td>
    <td>${record.timestamp}</td>
`;
tableBody.appendChild(row);
});
}

//Generate attendance summary file 
function saveAttendanceSummary() {
let attendanceData = "Attendance Summary\n\n";
attendanceRecords.forEach((r, i) => {
attendanceData += `${i + 1}. Username: ${r.username}\n   Timestamp: ${r.timestamp}\n\n`;
});

const blob = new Blob([attendanceData], { type: "text/plain" });
const link = document.createElement("a");
link.href = URL.createObjectURL(blob);
link.download = "attendance_summary.txt";
link.click();
}

//Download button event listener 
document.getElementById("downloadBtn").addEventListener("click", saveAttendanceSummary);