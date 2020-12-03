<template>

<body>
<div class="a"></div>

<div class="b">
    <div class="header">Header</div>
    <div class="register">
    <Card :bordered="false" dis-hover>
    <p slot="title" style="font-size:20px;">Reset password</p>
    <Form ref="formCustom" :model="formCustom" :rules="ruleCustom">
        <FormItem  prop="mail">
            <Input v-model="formCustom.mail" icon="email" style="width: 300px" placeholder="Enter your email">
            <p slot="prepend"  style="width:80px">Email</p></Input>
        </FormItem>

        <FormItem  prop="oldpass">
             <Input v-model="formCustom.oldpass" type="password" icon="locked" style="width: 300px" placeholder="Enter your old password">
             <p slot="prepend"  style="width:80px">Old Password</p></Input>
        </FormItem>

        <FormItem  prop="passwd">
            <Input v-model="formCustom.passwd" type="password" icon="locked" style="width: 300px" placeholder="Enter your new password">
            <p slot="prepend"  style="width:80px">New Password</p></Input>
        </FormItem>

        <FormItem  prop="passwdCheck">
            <Input v-model="formCustom.passwdCheck" type="password" icon="locked" style="width: 300px" placeholder="Confirm your new password">
            <p slot="prepend"  style="width:80px">Confirm</p></Input>
        </FormItem>

        <FormItem>
            <Button type="primary" :loading="loading" shape="circle" @click="handleSubmit('formCustom')" long>Reset password</Button>
        </FormItem>
        <a href="http://localhost:8080/#/login">cancel</a>
    </Form>
    </Card>
    </div>
    <div class="footer">2020-2020 © COMS554</div>
</div>
</body>
</template>


<script>
import axios from 'axios'
    export default {
        data () {
            const validateOldPass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please enter your old password'));
                } else {
                    callback();
                }
            };
            const validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please enter your password'));
                } else {
                    if (this.formCustom.passwdCheck !== '') {
                        this.$refs.formCustom.validateField('passwdCheck');
                    }
                    callback();
                }
            };
            const validatePassCheck = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please enter your password again'));
                } else if (value !== this.formCustom.passwd) {
                    callback(new Error('The two input passwords do not match!'));
                } else {
                    callback();
                }
            };


            return {
                loading: false,
                formCustom: {
                    name: '',
                    mail: '',
                    passwd: '',
                    passwdCheck: ''
                },
                ruleCustom: {
                    mail: [
                        { required: true, message: 'Mailbox cannot be empty', trigger: 'blur' },
                        { type: 'email', message: 'Incorrect email format', trigger: 'blur' }
                    ],
                    oldpass: [
                        { required:true,  message: 'The old password cannot be empty', validator: validateOldPass, trigger: 'blur' },
                        { type: 'string', min: 4, message: 'The password length cannot be less than 4 bits', trigger: 'blur' }
                    ],
                    passwd: [
                        { required:true,  message: 'The password cannot be empty', validator: validatePass, trigger: 'blur' },
                        { type: 'string', min: 4, message: 'The password length cannot be less than 4 bits', trigger: 'blur' }
                    ],
                    passwdCheck: [
                        { validator: validatePassCheck, trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleSubmit (name) {
                this.loading = true;
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        console.log(this.formCustom);
                        var that = this;
                        let param = new URLSearchParams();
                        param.append("email",this.formCustom.mail);
                        param.append("oldpass",this.formCustom.oldpass);
                        param.append("newpass",this.formCustom.passwd);
                        axios({
                              method:'post',
                              url: this.HOST+'/user/resetpass',
                              data: param,
                            })
                        .then(function(response) {
                                console.log(response);
                                if(response.data.message=="incorrect password"){
                                    that.$Message.info("incorrect password");
                                }
                                else if(response.data.message=="user not exist!"){
                                    that.$Message.info("user not exist!");
                                }
                                else if(response.data.message=="password reset!"){
                                    that.$Message.success('Success!');
                                    window.location.href='#/login';
                                }
                                that.loading = false;
                            })
                        .catch(function(error) {
                                that.$Message.error('Fail!');
                                console.log(error);
                                that.loading = false;
                            });
                    }
                    else {
                        this.$Message.error('Fail!');
                        this.loading = false;
                    }
                })
            }
        }
    }
</script>


<style>
.register{
    float: right;
    position:absolute;
    top: 20%;
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
}


</style>
