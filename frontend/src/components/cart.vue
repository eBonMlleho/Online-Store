<template>
  <div class="b">
  <div class="c">
    <br>
    <h1>My Cart</h1>
    <br>
       <table style="width:90%; margin-left:5%;">
        <tr>
          <th>Item id</th>
          <th>Item title</th>
          <th>Item price</th>
          <th>Seller id</th>
          <th>Cancel</th>
        </tr>
        <tr v-for="(item,index) in cart">
          <td>{{index}}</td>
          <td>{{item.item.title}}</td>
          <td>{{item.item.price}}</td>
          <td>{{item.item.userid}}</td>
          <td><button @click="remove(item.item.id)">delete</button></td>
          </tr>
      </table>
      <br>
      <div v-if="cart.length">
    <button @click="payall">Pay</button>
    </div>
    <br>
    <div v-if="cart.length">
   
  </div>
  <a href="http://localhost:8080/#/home" class="button">Home</a>
  </div>
  </div>
</template>

<script>
    import axios from 'axios';
    export default {

      data () {
        return {
          baseurl:"http://localhost:8000/",
          items:null,
          userid:null,
          username:null,
          useremail:null,
          cart:[],
        }
      },

      methods:{
          getcart(){
            var that = this;
            axios({
                  method:'GET',
                  url: this.HOST+"/cart/getcartitems?userid="+this.userid,
            }).then(function(response){
                console.log("cart",response);
                if(response.data.code==0){
                  alert(response.data.message);
                }
                else{
                  that.cart=response.data.carts;
                }
            }).catch(function(error){
                console.log(error);
            });
          },
          remove(id){
            var that = this;
            console.log(id);
            axios({
                  method:'GET',
                  url: this.HOST+"/cart/removeitemfromcart?userid="+this.userid+"&itemid="+id,
            }).then(function(response){
                console.log("cart",response);
                alert(response.data.message);
                location.reload();
            }).catch(function(error){
                console.log(error);
            });
          },
          payall(){
            var that = this;
             axios({
                  method:'GET',
                  url: this.HOST+"/cart/paybycart?buyerid="+this.userid,
            }).then(function(response){
                console.log("cart",response);
                if(response.data.code==0){
                  alert(response.data.message);
                }
                else{
                  location.reload();
                }
            }).catch(function(error){
                console.log(error);
            });
             that.getcart();
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
            this.getcart();
      },
    }
</script>


<style scoped>
.coupon{
  display: inline-block;
  width: 100px;
  height: 50px;
  font-size: 35px;
  text-align: center;
  font-weight: bold;
  color: red;
  border: 1px solid green;
}
.coupon button{
  font-size: 12px;
}
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
th, td {
  padding: 5px;
  text-align: left;
}
.b{
    position: center;
   
    width: 100%;
    height: 700px;
    display: block;
    background-image: url(/static/img/10.jpg);
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
a.button {
    -webkit-appearance: button;
    -moz-appearance: button;
    appearance: button;

    text-decoration: none;
    color: blue;
}
</style>
