<template>
  <div class="b">
  <div class="c">
  <br>
    <h1>My Order</h1>
    <br>
    <table style="width:90%; margin-left:5%;">
        <tr>
          <th>Item id</th>
          <th>Item title</th>
          <th>Item price</th>
          <th>Seller id</th>
          <th>Buyer id</th>
          <th>Cancel</th>
        </tr>
        <tr v-for="(order) in orders">
          <td>{{order.id}}</td>
          <td>{{order.title}}</td>
          <td>{{order.price}}</td>
          <td>{{order.userid}}</td>
          <td>{{order.buyerid}}</td>
          <td><button @click="returnorder(order.id)">return order</button></td>
        </tr>
    </table>
     <br>
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
          orders:null,
          userid:null

        }
      },

      methods:{
          returnorder(id){
            console.log(id);
            axios({
                  method:'GET',
                  url: this.HOST+"/item/return?buyerid="+this.userid+"&itemid="+id,
            }).then(function(response){
                console.log(response);
                alert(response.data.message);
                location.reload();
            }).catch(function(error){
                console.log(error);
            });
          }
      },
      mounted(){
            var that = this;
            this.userid= localStorage.getItem("userid");
            axios({
                  method:'GET',
                  url: this.HOST+"item/getorder?buyerid="+this.userid,
            }).then(function(response){
                console.log(response);
                if(response.data.code==0){
                  alert(response.data.message);
                }
                else{
                  that.orders=response.data
                }

            }).catch(function(error){
                console.log(error);
            });
      },
    }
</script>


<style scoped>
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
    height: 630px;
    display: block;
    background-image: url(/static/img/11.jpg);
    background-repeat: no-repeat;
    background-size: cover;
    overflow: hidden;
      min-height: 100%;
  min-width: 1024px;
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
