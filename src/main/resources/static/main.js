//------------------ALL-USERS--------------------------------

function getAllUsers() {

    fetch("http://localhost:8080/admin/users")
        .then((res) => res.json())
        .then((data) => {
            let temp = "";
            data.forEach(function (user) {

                temp += `
                <tr>
                <td id="id${user.id}">${user.id}</td>
                <td id="name${user.id}">${user.name}</td> 
                <td id="lastName${user.id}">${user.lastName}</td>
                <td id="age${user.id}">${user.age}</td>
                <td id="email${user.id}">${user.username}</td>
                <td>${user.roles.map(role => role.role.replace('ROLE_', ''))}</td>
                <td>
                <button class="btn btn-info btn-md" type="button"
                data-toggle="modal" data-target="#editModal" 
                onclick="fillModal(${user.id})">Edit</button></td>
                <td>
                <button class="btn btn-danger btn-md" type="button"
                data-toggle="modal" data-target="#deleteModal" 
                onclick="fillModal(${user.id})">Delete</button></td>
              </tr>`;
            });
            document.getElementById("usersTable").innerHTML = temp;
        })
}
getAllUsers()

//------------------fillModals--------------------------------

function fillModal(id) {
    fetch("http://localhost:8080/admin/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(res => {
        if (!res.ok) {
            throw new Error(`HTTP error! Status: ${res.status}`);
        }
        return res.json();
    })
        .then(user => {
            console.log("Received user data:", user);
            // res.json().then(user => {
            document.getElementById('id').value = user.id;
            document.getElementById('editName').value = user.name;
            document.getElementById('editLastname').value = user.lastName;
            document.getElementById('editAge').value = user.age;
            document.getElementById('editEmail').value = user.username;
            document.getElementById('editPassword').value = user.password;
            document.getElementById('delId').value = user.id;
            document.getElementById('delName').value = user.name;
            document.getElementById('delLastname').value = user.lastName;
            document.getElementById('delAge').value = user.age;
            document.getElementById('delEmail').value = user.username;
            document.getElementById('delPassword').value = user.password;
        })
        .catch(error => console.error('Error fetching user:', error));
    }


//------------------SHOW-User--------------------------------

function showUser() {
    //$("#topNav").css("display", "none");
    const showUserURL = 'http://localhost:8080/admin/user';
    fetch(showUserURL)
        .then((res) => res.json())
        .then((user) => {

            let temp = "<tr>";
            temp += `
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.username}</td>
                <td>${user.roles.map(role => role.role.replace('ROLE_', ''))}</td>
            `;
            temp += "<tr>";
            document.getElementById("userTable").innerHTML = temp;
        })
}
showUser();

//------------------NEW-USER--------------------------------

document.getElementById("newUserForm")
    .addEventListener("submit", newUserForm);

function newUserForm(e){
    e.preventDefault();

    let name = document.getElementById("addName").value;
    let lastName = document.getElementById("addLastname").value;
    let age = document.getElementById("addAge").value;
    let username = document.getElementById("addEmail").value;
    let password = document.getElementById("addPassword").value;
    let roles = selectRole(Array.from(document.getElementById("addRole").selectedOptions)
        .map(r => r.value));

    fetch("http://localhost:8080/admin/add", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: name,
            lastName: lastName,
            age: age,
            username: username,
            password: password,
            roles: roles
        })
    })
        .then(() => {
            document.getElementById("usersTab").click();
            getAllUsers();
            document.getElementById("newUserForm").reset();
        })
}
async function getUser(id){
    let response = await fetch(url + '/' + id);
    return await response.json();
}
//------------------EDIT--------------------------------

function butEdit() {

    let user = {
        id: document.getElementById('id').value,
        name: document.getElementById('editName').value,
        lastName: document.getElementById('editLastname').value,
        age: document.getElementById('editAge').value,
        username: document.getElementById('editEmail').value,
        password: document.getElementById('editPassword').value,
        roles: selectRole(Array.from(document.getElementById("editRole").selectedOptions)
            .map(r => r.value))
    }

    fetch("http://localhost:8080/admin/update", {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)

    })
    $("#editModal .close").click();
    reTable();
}

//------------------select-ROLE--------------------------------
function selectRole(r) {
    let roles = [];
    if (r.indexOf("USER") >= 0) {
        roles.push({"id": 1});
    }
    if (r.indexOf("ADMIN") >= 0) {
        roles.push({"id": 2});
    }
    return roles;
}

//------------------DELETE--------------------------------

function butDelete() {
    fetch("http://localhost:8080/admin/" + document.getElementById('delId').value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })

    $("#deleteModal .close").click();
    reTable();
}

//------------------reTable--------------------------------

function reTable() {
    // let table = document.getElementById('usersTable')
    // if (table.rows.length > 1) {
    //     table.deleteRow(1);
    // }
    setTimeout(getAllUsers, 140)

}
