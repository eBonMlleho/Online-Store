<template>
  <div class="b">
  <div class="c">
  <br>
    <h1>My Post</h1>
    <br>
    <table style="width:90%; margin-left:5%;">
        <tr>
          <th>Item id</th>
          <th>Item title</th>
          <th>Item price</th>
          <th>Seller id</th>
          <th>Cancel</th>
        </tr>
        <tr v-for="(post) in posts">
          <td>{{post.id}}</td>
          <td>{{post.title}}</td>
          <td>{{post.price}}</td>
          <td>{{post.userid}}</td>
          <td><button @click="deletepost(post.id)">delete post</button></td>
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
          posts:null,
          userid:null

        }
      },

      methods:{
          deletepost(id){
            console.log(id);
            axios({
                  method:'GET',
                  url: this.HOST+"/item/delete?id="+id,
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
                  url: this.HOST+"item/getpost?userid="+this.userid,
            }).then(function(response){
                console.log(response);
                if(response.data.code==0){
                  alert(response.data.message);
                }
                else{
                  that.posts=response.data
                }

            }).catch(function(error){
                console.log(error);
            });
      },
    }
</script>


<style scoped>
table{
  
  border-collapse: collapse;
   width: 100%;
}
th, td {
  border: 1px solid black;
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
