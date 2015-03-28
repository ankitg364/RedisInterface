# RedisInterface
GUI tool like phpmyadmin for performing various redis operations.

##Requirements

1.The project in build using java as backend language and would require maven to build the project and tomcat to deploy.
2.For monitoring a Redis Instance all the monitoring data would be stored on redis instance with port 6380. So u need to ensure that redis instance on port 6380 is active.

##Steps to Run the Project

1. Create an empty file and Specify file path in line 25 of file RedisInstances.java and line 21 of file DBRedisInfo.java in directory model.
2. Deploy the war created by maven on tomcat.
3. Open the Url http://localhost:8080/RedisInterfaceter/view/login.do (Change the Url accordingly).

## Quick Guide

1. After opening the url first of all add the instances you want to perform operations on by specifying the ip address and port and clicking on the add button.
2. After adding the instances you will see list of instances on left side with the active instances shown in bold characters.
3. You can click on any of the active instances and start performing various operations.

