fetch('user/user')
    .then((response) => {
            let res = response.json();
            return res;
    })
    .then((user) => {
            console.log(user);
            let roles = ""
            user.roles.forEach((role)=>{roles = roles + role.role + ' '});
            let tbody = document.getElementById('table_user');
            let tr = document.createElement('tr');
            tr.innerHTML = '<td>' + user.id + '</td>' +
                '<td>' + user.name + '</td>' +
                '<td>' + user.lastName + '</td>' +
                '<td>' + user.age + '</td>' +
                '<td>' + user.username + '</td>' +
                '<td>' + roles + '</td>';
            tbody.appendChild(tr);
    })
