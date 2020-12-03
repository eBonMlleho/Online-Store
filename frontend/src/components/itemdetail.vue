<template>

  <div>
    <h1>Item Detial</h1>
      <p>Title:</p>
      <span class="itemtext">{{item.title}}</span>
      <p>description:</p>
       <span class="itemtext">{{item.description}}</span>
      <p>price:</p>
      <span class="itemtext" style="color: red">${{item.price}}</span>
      <p>Seller:</p>
      <span class="itemtext" style="color: green">{{seller.email}}</span>
      <br>
      <button @click="buyitem" style="font-size: 20px;">Buy!</button>
      <br>
      <h2>Add comments</h2>
      <textarea placeholder="type your comments here" v-model="comment"></textarea>
      <br>
      <button @click="addcomment">submit</button>
      <br>
      <h2>Item comments</h2>
      <span class="itemtext" style="color: brown">{{item.comments}}</span>
      <br>
      <a href="http://localhost:8080/#/home" class="button">Home</a>
  </div>
</template>

<script>
    import axios from 'axios';
    export default {

      data () {
        return {
          baseurl:"http://localhost:8000/",
          itemid:null,
          item:null,
          seller:null,
          userid:null,
          comment:null,
          detail:null
        }
      },

      methods:{
        buyitem(){
              axios({
                  method:'GET',
                  url: this.HOST+"item/purchaseitem?userid="+this.userid+"&itemid="+this.itemid,
              }).then(function(response){
                  console.log(response);
                  alert(response.data.message);
              }).catch(function(error){
                  console.log(error);
              });
        },
        addcomment(){
            var that = this;
            console.log(this.comment);
            if(this.comment!=null){
               axios({
                    method:'GET',
                    url: this.HOST+"item/edit?id="+this.itemid+"&price="+this.item.price+
                    "&comments="+this.comment,
              }).then(function(response){
                  console.log(response);
                  if(response.data.code==1){
                    alert(response.data.message);
                  }
                  else{
                      alert(response.data.message);
                  }
                  that.seller = response.data;
              }).catch(function(error){
                  console.log(error);
              });
            }
        },
        getuser(id){
           var that = this;
             axios({
                  method:'GET',
                  url: this.HOST+"user/user?id="+id,
            }).then(function(response){
                console.log(response);
                that.seller = response.data;
            }).catch(function(error){
                console.log(error);
            });
          }
      },
      mounted(){
          this.itemid=this.$route.query.id,
          console.log(this.itemid);
          this.userid= localStorage.getItem("userid");
          var that = this;
          axios({
                method:'GET',
                url: this.HOST+"item/getitem?id="+this.itemid,
          }).then(function(response){
              console.log(response);
              if(response.data.message){
                alert(response.data.message);
              }
              else{
                that.item=response.data;
//                that.detail=that.item.description;
                that.getuser(response.data.userid);
                console.log(that.item);
              }
          }).catch(function(error){
              console.log(error);
          });
      },
    }
</script>


<style scoped>
.itemtext{
  font-size: 16px;
  font-weight: bold;
}
</style>
