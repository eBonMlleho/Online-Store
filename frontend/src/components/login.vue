<template>
<body>

    <div class="a">
    </div>

    <div class="b">

        <div class="login">
        <Card :bordered="false" dis-hover>
        <p slot="title" style="font-size:19px;">Welcome login</p>
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" >
            <FormItem  prop="mail" >
                <Input v-model="formValidate.mail" style="width: 300px" placeholder="Enter your email">
                <Icon slot="prepend"  type="email" style="width:20px" /></Input>
            </FormItem>

            <FormItem  prop="password" >
                <Input v-model="formValidate.password" type="password" style="width: 300px" placeholder="Enter your password">
                <Icon slot="prepend"  type="locked" style="width:20px" /></Input>
            </FormItem>

            <FormItem>
                <Button type="primary" :loading="loading" shape="circle" @click="handleSubmit('formValidate')" long>Login</Button>
                <a href="http://localhost:8080/#/reset">Reset password</a>
                <span>|</span>
                <a href="http://localhost:8080/#/signup">Register</a>
            </FormItem>

        </Form>
        </Card>
        </div>

        <div class="footer">2020-2020 © COMS554</div>
    </div>
</body>

</template>
<script>
import axios from 'axios'
axios.defaults.withCredentials=true;
    export default {
        data () {
            return {
                loading: false,
                formValidate: {
                    mail: '',
                    password: ''

                },
                ruleValidate: {

                    mail: [
                        { required: true, message: 'Mailbox cannot be empty',  trigger: 'blur' },
                        { type: 'email', message: 'Incorrect email format', trigger: 'blur' }
                    ],

                    password: [
                        { required: true, message: 'Please fill in the password.', trigger: 'blur' },
                    ]

                }
            }
        },
        methods: {
            handleSubmit (name) {
                this.loading = true;
                this.$refs[name].validate((valid) => {
                    if (valid) {

                        console.log(this.formValidate);
                        var that= this;
                        let param = new URLSearchParams();
                        param.append("email",this.formValidate.mail);
                        param.append("password",this.formValidate.password);
                        axios({
                              method:'post',
                              url: this.HOST+'/user/login',
                              data: param,
                            })
                        .then(function(response) {
                                console.log(response);
                                if(response.data.message=="login fail" || response.data.message=="user not exist!" ){
                                    that.$Message.error("Incorrect email or passowrd");
                                }
                                else if(response.data.message=="login success" || response.data.user.email==that.formValidate.mail){
                                    that.$Message.success('Success!');
                                    localStorage.setItem('username',response.data.user.name);
                                    localStorage.setItem('useremail',response.data.user.email);
                                    localStorage.setItem('userid',response.data.user.id);
                                    localStorage.setItem('islogin',"yes");
                                    window.location.href='#/home';
                                }
                                that.loading = false;
                            })
                        .catch(function(error) {
                                that.$Message.error('Fail error!');
                                console.log(error);
                                that.loading = false;
                            });
                    }
                    else {

                        this.$Message.error('Fail invaild!');
                        this.loading = false;

                    }

                })
            }
        }
    }


</script>


<style>

.login{
    float: right;
    position: absolute;
    top: 25%;
    right: 15%;
    text-align:center
}

.b{
    position: center;
   
    width: 100%;
    height: 700px;
    display: block;
    background-image: url(/static/img/9.jpg);
    background-repeat: no-repeat;
    background-size: 100% 100%;
    overflow: hidden;
}
.header{
    text-align: center;
    height: 50px;
    overflow: hidden;
}

.footer{
    position: absolute;
    text-align: center;
    width: 100%;
    height: 50px;
    bottom: 0px;
    overflow: hidden;
    color: #80848f;
    font-family: Helvetica Neue;
}

</style>
