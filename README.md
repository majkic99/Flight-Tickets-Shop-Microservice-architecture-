# Flight Tickets Shop (Microservice architecture)
 
### Features
This simulates an online flight ticket store, where backend is done with different SpringBoot Services grouped up in Microservice architecture, all connected to Eureka Service Discovery and behind a Zuul gateway. Frontend is done in JavaFX and it only communicates with API gateway.

User Service
-Responsible for Authorization and Authentication (Spring Security used, with JWT authorization).
-Registering, Logging in, Changing user data, adding credit cards to the user, admin verification.

Flight Service
-Admin can create and delete flights and airplanes. 
-Artemis message broker used for sending notification to other services about deleted flights.

Ticket Service
-Responsible for buying tickets, communicates with both other services via http requests.

Email Service
-Listens to activemq queues and is responsible for sending different types of notification emails


![](https://github.com/majkic99/Flight-Tickets-Shop-Microservice-architecture-/blob/main/screenshots/Start.png?raw=true)
![](https://github.com/majkic99/Flight-Tickets-Shop-Microservice-architecture-/blob/main/screenshots/Screenshot_1.png?raw=true)
![](https://github.com/majkic99/Flight-Tickets-Shop-Microservice-architecture-/blob/main/screenshots/Screenshot_2.png?raw=true)
![](https://github.com/majkic99/Flight-Tickets-Shop-Microservice-architecture-/blob/main/screenshots/Screenshot_3.png?raw=true)
![](https://github.com/majkic99/Flight-Tickets-Shop-Microservice-architecture-/blob/main/screenshots/Screenshot_4.png?raw=true)
