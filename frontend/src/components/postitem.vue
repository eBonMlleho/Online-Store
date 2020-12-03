<template>
  <div class="c">
  <div class="d">
  <Card :bordered="true" dis-hover>
  <p slot="title" style="font-size:21px;">Post New Item</p>

  <Form ref="formCustom" :model="formCustom" :rules="ruleCustom">


    <FormItem>
            <Input placeholder="Enter your item title" type="text" v-model="title">
            <p slot="prepend"  style="width:80px">Item Title:</p></Input>
    </FormItem>

    <FormItem>
            <Input placeholder="Enter your item description" type="text" v-model="desc">
            <p slot="prepend"  style="width:80px">Description:</p></Input>
    </FormItem>

     <FormItem>
            <Input placeholder="Enter price" type="text" v-model="price">
            <p slot="prepend"  style="width:80px">Price:</p></Input>
    </FormItem>

     <FormItem>
            <Button type="primary" :loading="loading" shape="circle" @click="post">POST</Button>
      </FormItem>

  </Form>

    <a href="http://localhost:8080/#/home">Home</a>
    </Card>
  </div>
  </div>
</template>

<script>
    import axios from 'axios';
    export default {

      data () {
        return {
          baseurl:"http://localhost:8000/",
          userid:null,
          title:null,
          desc:null,
          price:null,
        }
      },

      methods:{
          post(){
            console.log(this.category);
            axios({
                  method:'GET',
                  url: this.HOST+"item/post?userid="+this.userid+"&title="+this.title+
                  "&desc="+this.desc+"&price="+this.price,
            }).then(function(response){
                console.log(response);
                if(response.data.code==1){
                  alert(response.data.message);
                }
                else{
                  alert(response.data.message);
                }
            }).catch(function(error){
                console.log(error);
            });
          }
      },
      mounted(){
            var that = this;
            this.userid = localStorage.getItem("userid");
            console.log(this.userid);
      },
    }
</script>


<style>

.c{
    position: center;

    width: 100%;
    height: 700px;
    display: block;
    background-image: url(/static/img/10.jpg);
    background-repeat: no-repeat;
    background-size: 100% 100%;
    overflow: hidden;

}

.d{
    float: left;
    position:absolute;
    top: 20%;
    right: 40%;
    text-align:center

}

</style>
