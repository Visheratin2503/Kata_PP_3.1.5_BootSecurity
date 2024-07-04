fetch('user/user')
    .then(response => response.json())
    .then(user => {
        let roles = user.roles.map(role => role.role.replace('ROLE_', '')).join(', ');
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
    .catch(error => console.error('Error:', error));