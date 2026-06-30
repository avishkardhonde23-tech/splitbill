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

if (saveGroupBtn) {
    saveGroupBtn.addEventListener("click", createGroup);
}

if (saveMemberBtn) {
    saveMemberBtn.addEventListener("click", addMember);
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

loadGroups();
loadGroupDropdown();
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