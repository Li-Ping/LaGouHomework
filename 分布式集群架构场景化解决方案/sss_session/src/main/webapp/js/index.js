$(function () {
    $('#login-btn').on('click', ()=> {
        console.log('login');
        const name = $('#username').val();
        const password = $('#password').val();
        login(name, password);
    })

    function login(name, password) {
        $.ajax({
            url: '/login',
            method: 'post',
            data: { "name": name, "password": password },
            dataType: 'json',
            complete: (response) => {
                console.log(response);
                if (response.responseText === 'success') {
                    console.log('success');
                    window.location.href = '/resume/resume';
                } else {
                    alert(response.responseText);
                }
            }
        })
    }
})
