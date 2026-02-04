// Programmer: Sophia Welynne S. Eyas - 23-0118-315

// Hardcoded CSV data
const csvData = `
073900438,Osbourne,Wakenshaw,69,5,52,12,78
114924014,Albie,Gierardi,58,92,16,57,97
111901632,Eleen,Pentony,43,81,34,36,16
084000084,Arie,Okenden,31,5,14,39,99
272471551,Alica,Muckley,49,66,97,3,95
104900721,Jo,Burleton,98,94,33,13,29
111924392,Cam,Akram,44,84,17,16,24
292970744,Celine,Brosoli,3,15,71,83,45
107004352,Alan,Belfit,31,51,36,70,48
071108313,Jeanette,Gilvear,4,78,15,69,69
042204932,Ethelin,MacCathay,48,36,23,1,11
111914218,Kakalina,Finnick,69,5,65,10,8
074906059,Mayer,Lorenzetti,36,30,100,41,92
091000080,Selia,Rosenstengel,15,42,85,68,28
055002480,Dalia,Tadd,84,86,13,91,22
063101111,Darryl,Doogood,36,3,78,13,100
071908827,Brier,Wace,69,92,23,75,40
322285668,Bucky,Udall,97,63,19,46,28
103006406,Haslett,Beaford,41,32,85,60,61
104913048,Shelley,Spring,84,73,63,59,3
`;

// Parse CSV
let students = csvData.trim().split('\n').map(line => {
    const parts = line.split(',');
    return {
        id: parts[0],
        name: parts[1] + ' ' + parts[2],
        grade: parts.slice(3).join(', ')
    };
});

const tableBody = document.getElementById('tableBody');
const studentTable = document.getElementById('studentTable');
const txtID = document.getElementById('txtID');
const txtName = document.getElementById('txtName');
const txtGrade = document.getElementById('txtGrade');
const btnAdd = document.getElementById('btnAdd');
const btnDeleteSelected = document.getElementById('btnDeleteSelected');

let selectedIndex = null;

// Render table
function render() {
    tableBody.innerHTML = '';
    students.forEach((student, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.grade}</td>
        `;
        row.addEventListener('click', () => {
            selectedIndex = index;
            highlightRow(index);
        });
        tableBody.appendChild(row);
    });
}

// Highlight selected row
function highlightRow(index) {
    Array.from(tableBody.children).forEach((row, i) => {
        row.style.backgroundColor = i === index ? '#f0c040' : '';
    });
}

// Add new student
btnAdd.addEventListener('click', () => {
    const id = txtID.value.trim();
    const name = txtName.value.trim();
    const grade = txtGrade.value.trim();

    if (!id || !name || !grade) {
        alert('All fields are required!');
        return;
    }

    students.push({ id, name, grade });
    render();

    txtID.value = '';
    txtName.value = '';
    txtGrade.value = '';
    selectedIndex = null;
});

// Delete selected student
btnDeleteSelected.addEventListener('click', () => {
    if (selectedIndex === null) {
        alert('Select a row to delete.');
        return;
    }

    students.splice(selectedIndex, 1);
    selectedIndex = null;
    render();
});

// Initial render
render();
