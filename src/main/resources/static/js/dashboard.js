const userName = localStorage.getItem("userName");

document.getElementById("welcomeText").innerText =
    "Welcome, " + userName + " 👋";

function logout() {
    localStorage.clear();
    window.location.href = "/";
}

// =======================
// CREATE GROUP
// =======================
const saveGroupBtn = document.getElementById("saveGroupBtn");
const saveMemberBtn = document.getElementById("saveMemberBtn");
const saveExpenseBtn = document.getElementById("saveExpenseBtn");
const updateExpenseBtn = document.getElementById("updateExpenseBtn");

if (saveGroupBtn) {
    saveGroupBtn.addEventListener("click", createGroup);
}

if (saveMemberBtn) {
    saveMemberBtn.addEventListener("click", addMember);
}
if (saveExpenseBtn) {

    saveExpenseBtn.addEventListener("click", saveExpense);
}
if (updateExpenseBtn) {

    updateExpenseBtn.addEventListener("click", updateExpense);
}

async function createGroup() {

    console.log("Create button clicked");

    const groupName = document.getElementById("groupName").value.trim();

    if (groupName === "") {
        alert("Please enter group name");
        return;
    }

    const response = await fetch("/api/groups", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            groupName: groupName
        })
    });

    if (response.ok) {

        alert("Group created successfully!");

        document.getElementById("groupName").value = "";

        bootstrap.Modal.getInstance(
            document.getElementById("createGroupModal")
        ).hide();

        loadGroups();
        loadGroupDropdown();

    } else {

        alert("Failed to create group.");
    }
}

// =======================
// LOAD GROUPS
// =======================

async function loadGroups() {

    const response = await fetch("/api/groups");

    const groups = await response.json();

    const groupList = document.getElementById("groupList");

    groupList.innerHTML = "";

    groups.forEach(group => {

        groupList.innerHTML += `
            <div class="list-group-item d-flex justify-content-between align-items-center">

                <span>👥 ${group.groupName}</span>

                <button
                    class="btn btn-sm btn-success"
                    onclick="openAddMemberModal(${group.id})">
                    Add Member
                </button>

            </div>
        `;

    });

}

// =======================
// LOAD GROUP DROPDOWN
// =======================

async function loadGroupDropdown() {

    const response = await fetch("/api/groups");

    const groups = await response.json();

    const dropdown = document.getElementById("expenseGroup");

    dropdown.innerHTML = "";

    groups.forEach(group => {

        dropdown.innerHTML += `
            <option value="${group.id}">
                ${group.groupName}
            </option>
        `;

    });

}
// =======================
// LOAD VIEW GROUP DROPDOWN
// =======================

async function loadViewExpenseGroups() {

    const response = await fetch("/api/groups");

    const groups = await response.json();

    const dropdown = document.getElementById("viewExpenseGroup");

    dropdown.innerHTML = "";

    groups.forEach(group => {

        dropdown.innerHTML += `
    <option value="${group.id}">
        ${group.groupName}
    </option>
`;

    });

}
// =======================
// OPEN ADD MEMBER MODAL
// =======================

async function openAddMemberModal(groupId) {

    document.getElementById("selectedGroupId").value = groupId;

    await loadUsers();

    const modal = new bootstrap.Modal(
        document.getElementById("addMemberModal")
    );

    modal.show();

}

// =======================
// INITIAL PAGE LOAD
// =======================

async function loadUsers() {

    const response = await fetch("/api/users");

    const users = await response.json();

    const dropdown = document.getElementById("memberDropdown");

    dropdown.innerHTML = "";

    users.forEach(user => {

        dropdown.innerHTML += `
            <option value="${user.id}">
                ${user.name}
            </option>
        `;

    });

}
async function loadPaidByDropdown() {

    const response = await fetch("/api/users");

    const users = await response.json();

    const dropdown = document.getElementById("paidBy");

    dropdown.innerHTML = "";

    users.forEach(user => {

        dropdown.innerHTML += `
            <option value="${user.id}">
                ${user.name}
            </option>
        `;

    });

}
async function addMember() {
    console.log("Add Member button clicked");


    const groupId = document.getElementById("selectedGroupId").value;

    const userId = document.getElementById("memberDropdown").value;

    const response = await fetch("/api/group-members", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({

            groupId: groupId,

            userId: userId

        })

    });

    if (response.ok) {

        alert("Member added successfully!");

        bootstrap.Modal.getInstance(
            document.getElementById("addMemberModal")
        ).hide();

    } else {

        alert("Failed to add member.");
    }

}
// =======================
// SAVE EXPENSE
// =======================
async function saveExpense() {

    const groupId = document.getElementById("expenseGroup").value;
    const description = document.getElementById("expenseDescription").value;
    const amount = document.getElementById("expenseAmount").value;
    const paidBy = document.getElementById("paidBy").value;

    const response = await fetch("/api/expenses", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({

            groupId: Number(groupId),
            paidBy: Number(paidBy),
            amount: Number(amount),
            description: description,

            splitType: "EQUAL"

        })

    });

    if (response.ok) {

        alert("Expense Added Successfully!");

        bootstrap.Modal.getInstance(
            document.getElementById("expenseModal")
        ).hide();

        await loadViewExpenseGroups();

        document.getElementById("viewExpenseGroup").value = groupId;

        await loadExpenses();

    }

     else {

        const error = await response.text();
        alert(error);

    }

}
// =======================
// LOAD EXPENSES
// =======================

async function loadExpenses() {

    const groupId = document.getElementById("viewExpenseGroup").value;
    console.log("Group ID =", groupId);
    console.log(`/api/expenses/group/${groupId}`);

    const response = await fetch(
        `/api/expenses/group/${groupId}?t=${new Date().getTime()}`
    );

    const page = await response.json();

    console.log(page);

    const tbody = document.getElementById("expenseTableBody");

    tbody.innerHTML = "";

    page.content.forEach(expense => {

        tbody.innerHTML += `
<tr>
    <td>${expense.description}</td>
    <td>${expense.amount}</td>
    <td>${expense.paidBy}</td>

    <td>
        <button
            class="btn btn-sm btn-warning"
            onclick="openEditExpense(${expense.id},
                                     '${expense.description}',
                                     ${expense.amount})">
            Edit
        </button>
        
         <button
        class="btn btn-sm btn-danger"
        onclick="deleteExpense(${expense.id})">
        Delete
    </button>
    </td>

</tr>
`;

    });

}
function openEditExpense(id, description, amount) {

    document.getElementById("editExpenseId").value = id;

    document.getElementById("editDescription").value = description;

    document.getElementById("editAmount").value = amount;

    const modal = new bootstrap.Modal(
        document.getElementById("editExpenseModal")
    );

    modal.show();

}
async function updateExpense() {

    const expenseId = document.getElementById("editExpenseId").value;

    const description = document.getElementById("editDescription").value;

    const amount = document.getElementById("editAmount").value;

    const response = await fetch(`/api/expenses/${expenseId}`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({

            description: description,
            amount: Number(amount)

        })

    });

    if (response.ok) {

        alert("Expense Updated Successfully!");

        bootstrap.Modal.getInstance(
            document.getElementById("editExpenseModal")
        ).hide();

        await loadExpenses();

    } else {

        alert("Failed to update expense.");

    }

}
async function deleteExpense(expenseId) {

    const confirmDelete = confirm("Are you sure you want to delete this expense?");

    if (!confirmDelete) {
        return;
    }

    const response = await fetch(`/api/expenses/${expenseId}`, {

        method: "DELETE"

    });

    if (response.ok) {

        alert("Expense Deleted Successfully!");

        await loadExpenses();

    } else {

        alert("Failed to delete expense.");

    }

}
async function loadBalanceGroups() {

    const response = await fetch("/api/groups");
    const groups = await response.json();

    const dropdown = document.getElementById("balanceGroup");

    dropdown.innerHTML = "";

    groups.forEach(group => {

        dropdown.innerHTML += `
            <option value="${group.id}">
                ${group.groupName}
            </option>
        `;

    });

}
async function loadBalances() {

    const groupId = document.getElementById("balanceGroup").value;

    const response = await fetch(`/api/splits/balances/${groupId}`);

    const balances = await response.json();

    const tbody = document.getElementById("balanceTableBody");

    tbody.innerHTML = "";

    balances.forEach(balance => {

        tbody.innerHTML += `
            <tr>
                <td>${balance.memberName}</td>
                <td>₹${balance.paid}</td>
                <td>₹${balance.share}</td>
                <td>₹${balance.balance}</td>
            </tr>
        `;

    });

}
async function openSettleModal(memberName, balance) {

    const users = await fetch("/api/users");
    const userList = await users.json();

    const fromUser = document.getElementById("fromUser");
    const toUser = document.getElementById("toUser");

    fromUser.innerHTML = "";
    toUser.innerHTML = "";

    userList.forEach(user => {

        fromUser.innerHTML += `
            <option value="${user.id}">
                ${user.name}
            </option>
        `;

        toUser.innerHTML += `
            <option value="${user.id}">
                ${user.name}
            </option>
        `;
    });

    // Select the member passed from the Balance table
    const member = userList.find(u => u.name === memberName);

    if (member) {
        fromUser.value = member.id;
    }

    // Default receiver = first different user
    const receiver = userList.find(u => u.id != fromUser.value);

    if (receiver) {
        toUser.value = receiver.id;
    }

    document.getElementById("settleAmount").value = Math.abs(balance);

    new bootstrap.Modal(
        document.getElementById("settleModal")
    ).show();
}
document.getElementById("settleBtn").addEventListener("click", async () => {

    const request = {
        fromUserId: Number(document.getElementById("fromUser").value),
        toUserId: Number(document.getElementById("toUser").value),
        amount: Number(document.getElementById("settleAmount").value)
    };

    const response = await fetch("/api/splits/settle", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(request)
    });

    if (response.ok) {

        alert("Settlement Successful");

        bootstrap.Modal.getInstance(
            document.getElementById("settleModal")
        ).hide();

        loadBalances();

    } else {

        alert(await response.text());

    }
});
async function openViewExpenseModal() {

    await loadViewExpenseGroups();

    await loadExpenses();

}
loadGroups();
loadGroupDropdown();
loadViewExpenseGroups();
loadPaidByDropdown();