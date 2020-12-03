<template>
  <div class="b">
  <div class="c">
  <br><br>
    <div class="header">
      <a style="font-size: 20px;font-weight: bold;">{{useremail}}</a>
      <input type="text" v-model="searchkey" placeholder="search items"><button @click="searchitem" style="margin-right: 100px;">Search</button>
      <a style="margin-right: 20px; font-size: 20px;" href="#/postitem">Post Item</a>
      <a style="margin-right: 20px; font-size: 20px;" href="#/mypost">My Post</a>
      <a style="margin-right: 20px; font-size: 20px;" href="#/myorder">My Order</a>
      <a style="margin-right: 20px; font-size: 20px;" href="#/cart">My Cart</a>
      <button style="margin-left: 15%" class="button" @click="logout">Logout</button>
    </div>
    <div class="items">
      <div class="item" v-for="(item,index) in items">
        <p>title:</p>
        <p class="item_text">{{item.title}}</p>
        <p>description:</p>
         <p class="item_text">{{item.description}}</p>
        <p>price:</p>
         <p class="item_text" style="color: red">${{item.price}}</p>
         <button @click="jump(item.id)">More Detail</button>
         <button @click="additem(item.id)">add to cart</button>
      </div>
    </div>
   </div>
  </div>
</template>



<script>
    import axios from 'axios';
    import SockJS from  'sockjs-client';
    import  Stomp from 'stompjs';
    export default {

      data () {
        return {
          baseurl:"http://localhost:8000/",
          userid:null,
          useremail:null,
          username:null,
          items:null,
          suggestitem:null,
          searchkey:null,
          modal1: false,
          modal2:false,
          message: null
        }
      },

      methods:{
        jump(itemid){
          console.log(itemid);
          this.$router.push({path:'/itemdetail',query: {id:itemid}});
        },
        searchitem(){
          var that = this;
          let param = new URLSearchParams();
          if(this.searchkey!=null){
               param.append("key",this.searchkey);
            axios({
                  method:'post',
                  url: this.HOST+"/item/search",
                  data: param,
            }).then(function(response){
                console.log(response);
                if(response.data.message){
                    alert(response.data.message);
                }
                else{
                  that.items=response.data;
                }

            }).catch(function(error){
                console.log(error);
            });
          }
        },
        additem(id){
          console.log(id);
          console.log(this.HOST);
          let param = new URLSearchParams();
          param.append("userid",this.userid);
          param.append("itemid",id);
            axios({
                  method:'post',
                  url: this.HOST+"/cart/additemtocart",
                  data: param,
            }).then(function(response){
                console.log(response);
                alert(response.data.message);

            }).catch(function(error){
                console.log(error);
            });
        },
        logout(){
          localStorage.removeItem("useremail");
          localStorage.removeItem("userid");
          localStorage.removeItem("username");
          axios({
               method:'post',
               url: this.HOST+"user/logout?email="+this.useremail,
          }).then(function(response){
               console.log("log out",response);

          }).catch(function(error){
                          console.log(error);
          });
          window.location.href='#/login';
        },
        ok () {
          this.$Message.info('Clicked ok');
        },
        cancel () {
          this.$Message.info('Clicked cancel');
        }
      },
      mounted(){
            var that = this;
            this.useremail=localStorage.getItem("useremail");
            var userid = localStorage.getItem("userid");
            this.userid=userid;
            this.username = localStorage.getItem("username");
            console.log("useremail",this.useremail,this.username,this.userid);
            console.log("username",this.username);
            axios({
                  method:'GET',
                  url: this.HOST+"item/getallitems",
            }).then(function(response){
                console.log(response);
                that.items=response.data;

            }).catch(function(error){
                console.log(error);
            });
      },
    }
</script>


<style scoped>
.selectfriend{
   background-color: #c5c8ce;
}
.firendlist{
  width: 20%;
  height: 220px;
  float: left;
  overflow-y: scroll;
  overflow-x: hidden;
}
.friend{
  height: 30px;
  border: 1px solid #515a6e;
}
.friend:hover{
  background-color: #c5c8ce;
}
.chatborder{
  width: 100%;
  height: auto;
  text-align: right;
}
.innertext{
  margin-left: 4px;
  color: white;
  font-size: 20px;
}
.speech-bubble-left{
  margin: 5px;
  position: relative;
  background: #00aabb;
  border-radius: .4em;
  width: auto;
  display: inline-block !important;
  display:inline;
}
.speech-bubble-left:after {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  width: 0;
  height: 0;
  border: 0.313em solid transparent;
  border-right-color: #00aabb;
  border-left: 0;
  margin-top: -0.312em;
  margin-left: -0.312em;
}
.speech-bubble-right{
  margin: 5px;
  position: relative;
  background: yellowgreen;
  border-radius: .4em;
  text-align: right;
  padding-right: 5px;
  width: auto;
  display: inline-block !important;
  display: flex;
  right: 0;

}
.speech-bubble-right:after {
  text-align: right;
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  width: 0;
  height: 0;
  border: 0.313em solid transparent;
  border-left-color: yellowgreen;
  border-right: 0;
  margin-top: -0.312em;
  margin-right: -0.312em;

}


.chatbox{
  border-radius: 10px;
  border: 1px solid #5cadff;
  width: 80%;
  height:220px;
  margin-bottom: 10px;
  overflow: scroll;
  overflow-x:hidden;
  overflow-y:visible;

}
.header{
  height: 50px;
}
.item{
  display: table-cell;
  width: 200px;
  height: auto;
  border: 1px solid red;
}
.items{
    display: table;
    border: 1px solid black;
}
.item_text{
  font-size: 16px;
  font-weight: bold;
}


.b{
    position: center;
   
    width: 100%;
    height: 700px;
    display: block;
    background-image: url(/static/img/12.jpg);
    background-repeat: no-repeat;
    background-size: 100% 100%;
    overflow: hidden;
}
.c{
    float: left;
    width: 100%;
    position: center;
    text-align:center;
}

.button {
  background-color: grey; /* Green */
  border: none;
  color: white;
  padding: 8px 18px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  cursor: pointer;
}

</style>
