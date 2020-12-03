<template>

<body>

<div class="b">
  
    <div class="register">
    <Card :bordered="false" dis-hover>
    <p slot="title" style="font-size:18.5px;">Welcome sign up</p>
    
    <Form ref="formCustom" :model="formCustom" :rules="ruleCustom">
        <FormItem  prop="name">
            <Input v-model="formCustom.name" icon="person" style="width: 300px" placeholder="Enter your name">
            <p slot="prepend"  style="width:50px">Name</p></Input>
        </FormItem>

        <FormItem  prop="mail">
            <Input v-model="formCustom.mail" icon="email" style="width: 300px" placeholder="Enter your email">
            <p slot="prepend"  style="width:50px">Email</p></Input>
        </FormItem>

        <FormItem  prop="passwd">
            <Input v-model="formCustom.passwd" type="password" icon="locked" style="width: 300px" placeholder="Enter your password">
            <p slot="prepend"  style="width:50px">Password</p></Input>
        </FormItem>

        <FormItem  prop="passwdCheck">
            <Input v-model="formCustom.passwdCheck" type="password" icon="locked" style="width: 300px" placeholder="Confirm your password">
            <p slot="prepend"  style="width:50px">Confirm</p></Input>
        </FormItem>

        <FormItem>
            <Button type="primary" :loading="loading" shape="circle" @click="handleSubmit('formCustom')" long>Sign up</Button>
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
                    name: [
                        { required: true, message: 'The name cannot be empty', trigger: 'blur' }
                    ],
                    mail: [
                        { required: true, message: 'Mailbox cannot be empty', trigger: 'blur' },
                        { type: 'email', message: 'Incorrect email format', trigger: 'blur' }
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
                        param.append("name",this.formCustom.name);
                        param.append("email",this.formCustom.mail);
                        param.append("password",this.formCustom.passwd);
                        axios({
                              method:'post',
                              url: this.HOST+'/user/signup',
                              data: param,
                            })
                        .then(function(response) {
                                console.log(response);
                                if(response.data.message=="user exist!"){
                                    that.$Message.info("Email exist, please use another email");
                                }
                                else if(response.data.message=="signup success"){
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
    float: left;
    position:absolute;
    top: 20%;
    right: 20%;
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

    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
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
    color: white;
}


</style>
