# Online-Store
Web App. Front-end: Vue.js. Back end: Spring Boot

## Introduction
Use Vue.js as frontend, while using Spring Boot as a backend. We build this website serve as a platform for users to sell their products. This middle-scale software provides practice for learning microservices and decomposing monolithic applications. Client-server model is a distributed application structure that partitions tasks and workloads between server and clients. 

Spring Cloud Loadbalancer is a generic abstraction that can do the work that we used to do with Netflix’s Ribbon project which are often employed to scale the server. Although peer to peer is getting popular these days, Client-server model can still dominate market for years. And use Spring Cloud to host our application to make more scalable is our next step.

## Build Setup & How to Run
Setup and configure MySQL database  
a.	Port number  
b.	Database url  
c.	Username  
d.	Password  

backend\src\main\resources filling corresponding parameters.

npm install

npm dev run

Open browser and go to url: http://localhost:8080/#/login to start.

## Architecture
![GitHub Logo](/flowchart.png)

