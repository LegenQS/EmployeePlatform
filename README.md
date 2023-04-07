# EmployeePlatform

This is an onboarding platform for internal usage, providing document checking, housing allocation, facility report, email notification. Developed by Spring Boot and Java. The tech stack with architecture includes:
- `Database`: SQL, MongoDB, Hibernate, AWS RDS
- `Framework`: Spring Boot
- `Tools`: RabbitMQ, AWS, Gateway and Eureka

## How to run
Configure your running configuration with the following VM options command to enable decryption of the credentials.
```
  -Djasypt.encryptor.password=YourJasyptKey
```
You should also change all the credentials in the `.properties` or `.yml` files to the credentials either for your local databases or the AWS RDS service by
```
  java -cp //jasypt-1.9.3/lib/jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input=”xyz123″ password=secretkey algorithm=PBEWithMD5AndDES
```

Run your local RabbitMQ by 
```
  brew services start rabbitmq
```
And be sure you have set up the proper queues, exchange stategy and routing keys at [here](https://github.com/LegenQS/EmployeePlatform/blob/main/Microservice/EmailService/src/main/java/com/qs/emailservice/controller/EmailController.java).  
