window.onload = function() {
    let captchaInterval = null
    let captchaTimer = 60
    
    $('#go-to-register').on('click', function() {
        $('.login').hide();
        $('.register').css('display', 'flex');
    })
    
    $('#go-to-login').on('click', function() {
        $('.register').hide();
        $('.login').css('display', 'flex');
    })
    
    $('.my-input[name=email]').on('input', function() {
        valiForm('email')
    })
    
    $('.my-input[name=password]').on('input', function() {
        valiForm('password')
    })
    
    $('.my-input[name=confirm-password]').on('input', function() {
        valiForm('confirm-password')
    })
    
    $('.my-input[name=captcha]').on('input', function() {
        valiForm('captcha')
    })
    
    $('#get-captcha').on('click', function() {
        geCaptcha()
    })
    
    $('#do-login').on('click', function() {
        const email = $('.my-input[name=email]').val()
        const pswd = $('.my-input[name=password]').val()
        
        if (valiForm('email') && valiForm('password')) {
            $.ajax({
                url: `/api/user/login/${email}/${pswd}`,
                method: 'get',
                success: function(res) {
                    if(res) {
                        window.location.href = `welcome.html?user=${res}`
                    } else {
                        alert('用户不存在或密码错误！')
                    }
                }
            })
        } 
    })
    
    $('#do-register').on('click', function() {
        const email = $('.my-input[name=email]').val()
        const pswd = $('.my-input[name=password]').val()
        const code = $('.my-input[name=captcha]').val()
        if (valiForm('email') && valiForm('password') && valiForm('confirm-password') && valiForm('captcha')) {
            $.ajax({
                url: `/api/user/isRegistered/${email}`,
                method: 'get',
                success: function(res) {
                    console.log(res)
                    if(!res) {
                        $.ajax({
                            url: `/api/user/register/${email}/${pswd}/${code}`,
                            method: 'get',
                            success: function(res) {
                                if(res) {
                                    window.location.href = `welcome.html?user=${res}`
                                } else {
                                    alert('注册失败！')
                                }
                            }
                        })
                    } else {
                        alert('该邮箱已注册！')
                    }
                    
                }
            })
            
        } 
    })
    
    function valiForm(name) {
        let result = false
        let warningText = ''
        if (name === 'email') {
            const emialReg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
            const email = $(`.my-input[name=${name}]`).val()
            if (email.length === 0) {
                warningText = '邮箱不能为空'
            } else {
                result = emialReg.test(email)
                if(!result) {
                   warningText = '邮箱格式不正确！' 
                }
            }
            
        } else if (name === 'password') {
            const pswd = $('.my-input[name=password]').val()
            if (pswd.length === 0) {
                warningText = '密码不能为空'
            } else if (pswd.length < 4) {
                warningText = '密码长度至少为5！'
            } else if (pswd.length > 20) {
                warningText = '密码长度小于20！'
            } else {
                result = true
            }
        } else if (name === 'confirm-password') {
            const pswd = $('.my-input[name=password]').val()
            const rpswd = $('.my-input[name=confirm-password]').val()
            if (rpswd.length === 0) {
                warningText = '请填写确认密码'
            } else if (pswd !== rpswd) {
                warningText = '两次输入密码不一致！'
            } else {
                result = true
            }
        } else if (name === 'captcha') {
            const code = $('.my-input[name=captcha]').val()
            if (code.length === 0) {
                warningText = '验证码不能为空'
            } else {
                result = true
            }
        }
        
        if(!result) {
            $(`.my-input[name=${name}] + .form-text`).text(warningText)
        } else if(name === 'captcha'){
            if(captchaInterval === null) {
                $(`.my-input[name=${name}] + .form-text`).text('重新获取')
            }
        } else {
            $(`.my-input[name=${name}] + .form-text`).text('')
        }
        return result
    }
    
    function geCaptcha() {
        if(captchaInterval === null) {
            changeCaptchaStatus()
            const email = $('.my-input[name=email]').val()
            $.ajax({
                url: `/api/code/create/${email}`,
                method: 'get',
                success: function(res) {
                    if(!res) {
                        alert('获取验证码失败！')
                    }
                }
            })
        }
        
        function changeCaptchaStatus() {
            $('#get-captcha').text('已发送')
            captchaInterval = setInterval(function() {
                $('#get-captcha').text(`${--captchaTimer}秒之后重发`)
                if(captchaTimer === 0) {
                    captchaTimer = 60
                    clearInterval(captchaInterval)
                    $('#get-captcha').text('获取验证码')
                    captchaInterval = null
                }
            }, 1000)
        }
    }
     
}
