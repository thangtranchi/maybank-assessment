## ENVIRONMENT SETUP
- Java 17
- Maven
- Postman or any application to test RESTful API

## Spring framework using
- Spring boot 3.0.2
- Spring batch
- Spring security 
- Spring hateoas
- Spring data


## HOW TO START APP
- Run as Java Application file StartUpApplication.java

## HOW TO TEST APP
### Test import file
- The dataSource.txt was copied to *mb-assessment-tcthang\src\main\resources\dataSource.txt*, when app starts, this file will consumes and import automatically
- Application uses H2-embedded, then we don't need to setup Database. To access DB using this link [http://localhost:8080/h2-console](http://localhost:8080/h2-console) 
- Username/Password for access : admin/123456
- JDBC URL : jdbc:h2:mem:testdb (username/password set to default, don't need to change)
- Query to run : select * from transaction

### Test RESTful API
- BASIC AUTHENTICATION : username/password  : admin/123456
- Retrieve REST API entry point : GET [localhost:8080/transaction/search?account=1111111111,8872838283&desc=TRANSFER&custId=123](http://localhost:8080/transaction/search?account=1111111111,8872838283&desc=TRANSFER&custId=123)
- Update REST API entry point : PUT [localhost:8080/transaction/updateDesc](http://localhost:8080/transaction/updateDesc)
- Update body request sample

```xml
{
    "id":1,
    "description":"New DESCRIPTION",
    "oldDescription":"FUND TRANSFER"
}
```


