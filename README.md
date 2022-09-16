# Architecture
This project is derived from a reference architecture for microservies.

A very important principles in Micro-Services architecture are loose coupling and high cohesion.

It is called “micro” because it meant to be cohesive with respect to a limited set of functionality.

It is loosely coupled in the sense that a micro-service typically is the owner of an unshared database. 
This allows the micro service to own changes on a database without impacting other applications, 
and it also allows for a service to scale independently on as needed basis.

![Microservices Architecture](src/main/docs/images/micro.png)

## Layered Architecture

The actual Micro-Service component is typically built using a layered architecture.

Len Bass, Paul Clements, and Rick Kazman best explain the Layered Architecture as, “The Layered Architecture Pattern 
defines layers as a grouping of modules or services and a unidirectional allowed-to-use relation among layers. 
Layers must have a strict-ordering relation, in which a layer can only use public interfaces of a layer below,” 
in the book Software Architecture in Practice1.

The benefit of the layered-architecture is that it allows for a separation of concerns, allows for a division of 
labor, and it is typically easy to refactor a top layer.

Its drawback is that there is a performance cost for each layer, and it can be very expensive to refactor lower layers.

![Layered Architecture Pattern](src/main/docs/images/layers.png)

## Layers within a Typical Spring Boot Micro-Services Application

This influences how we should organize a project within a Spring Boot Micro-Services application.

![Spring Boot Layers](src/main/docs/images/sb-layers.png)

### The Presentation Tier

A well-designed architecture could easily switch Presentation Layer implementations with different technologies. 
Perhaps this could be a Swing Application, Command Line Application, or MVC framework with server side templating. 
This particular example documents a Layered REST application.

### Serialization Segment

The Serialization segment provides a set of classes per each serializable request and response types 
(i.e. Plain Ordinary Java Object that Jackson could easily convert to and from JSON).

### Transformer Segment

The Transformer segment will convert serializable request and response classes into Domain / Model classes.

### REST Endpoint Declarations Segment

The REST Endpoint Declarations will each declare a PATH, an Accept type, a Content-Type, a serializable 
request class, and a serializable response class.

It will receive the Serializable Request, transform it into the appropriate Domain / Model class, and call the 
appropriate Business Logic Service class, transform the result into Serializable Response, and finally respond.

### Business Logic Services Layer

The Business Logic Services Layer receives a domain object from the Presentation Layer, and provides business 
functionality. It will use the Client Interface to communicate to other components in a system or the 
Persistence Layer to persist data.

### Client Interface Layer or Persistence Layer

The Client Interface Layer is responsible for sending or receiving data from other systems.  This might also be 
a Persistence Layer that merely persist data into a data-store. For example, this could be a set of 
Data-access Objects that use JPA.

### Domain / Model Layer

The Domain / Model layer should attempt to provide an object-oriented model of your business. It is good to 
implement business logic here, but keep in mind that business logic computations often require external 
persistence, and it is anti-pattern to couple persistence technologies with a domain object. These 
absolutely should be Plain Ordinary Java objects, and depending on your threading model, perhaps immutable as well.

## Package Structure

![Package Structures](src/main/docs/images/packages.png)

### suffix.company.product.component

This is the base package. It will also contain the application class ComponentNameApp.

### suffix.company.product.component.api.domain

Contains concrete domain objects, model classes, or business objects.

### suffix.company.product.component.api.services

Contains interfaces for each service.

### suffix.company.product.component.api.repositories

Contains interfaces for each Data-Access Object (aka Data Repositories).

Since we might have eventually have two implementations, please prefix them with the type of Repository, for example JdbcTimeSeriesRepository and CassandraTimeSeriesRepository which both implement some Repository interface.

These should provide CRUD operations, thus, we should use the following names:
`createOne`, `createMany`, `updateOne`, `updateMany`, `removeById`

Use find methods instead of get.  Make sure to use `By` and `And`. For example:  `findTimeSeriesById`, `findTimeSeriesByOrgAndPlanDateRange`

### suffix.company.product.component.implementation.controllers

This will contain all the @RestController classes. Each method will typically follow these steps:

Transform IO Request Transfer Object POJO into one or more domain objects.

Call the appropriate Service implementation that contains the business logic.

Transform the domain object into the appropriate IO Response Transfer Object POJO.

Respond with the proper HTTP Code.

Rest Resources Coding Conventions

Resources are an HTTP paths that accept verbs.  The only verbs that it accepts are `POST`, `PUT`, `GET`, `DELETE`, `OPTIONS`, and `PATCH`.  `PUT`, `GET`, and `DELETE` are idempotent operations.  For this reasons, `POST`, `PUT`, `GET`, `DELETE` usually correspond with create, update, get, delete, respectively.

<span style="color:red">DO NOT USE verb names for rest resources.</span>
<span style="color:red">NEVER return true/false flags to indicate something succeeded or failed</span>. Instead, use the correct HTTP response codes (you can review them at this reference).
<span style="color:red">Absolutely no business logic nor persistence logic will exist in this layer</span>. Business Logic will be delegated to Service layer.

`GET` operations should correspond to find method names. <span style="color:red">Do not use the term `get` in controller method names because in Java `get` should be used for properties</span>.

`POST` operations should correspond to create method names.

`PUT` operations should correspond to update method names.

`DELETE` operations should correspond to remove method names.

If a method retrieves or updates many, please use the word many in the method name, for example `findManyByOrganization`.

You can also use the term all if your method retrieves all, such as `findAll`.

If the method updates only one entity, then please use the word one in the method name, for example `findOneById`.

### suffix.company.product.component.implementation.controllers.io

This will contain any POJOs that are used in the HTTP Request / Response.  We use POJOs here because they are easily serialized with Jackson. I expect each supported HTTP Method to have its own Request and Response POJO.

#### Controller IO Naming Conventions

1. Use the suffix Request for requests, and Response for responses.
2. Use the word Find, Create, Remove in an HTTP GET, DELETE respectively, but use the HTTP method name for other objects. 
3. Use the word One for singular, and Many for plural.

##### Controller IO Naming Convention Examples

| Method | Resource          | Request Object             | Response Object                                                                 |
|--------|-------------------|----------------------------|---------------------------------------------------------------------------------|
| GET    | /pluralNouns      | N/A                        | FindManyPluralNounsResponse                                                     |
| GET    | /pluralNouns/{id} | N/A                        | FindOneSingularNounResponse                                                     |
| PUT    | /pluralNouns/{id} | PutOneSingularNounRequest  | PutOneSingularNounResponse. This just return 200 and require a response object. |
| POST   | /pluralNouns      | PostOneSingularNounRequest | PostOneSingularNounResponse                                                     |
| DELETE | /pluralNouns/{id} | N/A                        | RemoveOneSingularNounResponse (realistically, this will have no response)       |

### suffix.company.product.component.implementation.repositories
This will contain concrete Data-Access objects used by the Services.

Distinguish different implementations by prefixing them with the type of Repository, for example JdbcTimeSeriesRepository and CassandraTimeSeriesRepository which both implement some Repository interface.

These should provide CRUD operations, thus, we should use the following names:
`createOne`, `createMany`, `updateOne`, `updateMany`, `removeById`

Use find methods instead of get.  Make sure to use By and And. For example:  `findTimeSeriesById`, `findTimeSeriesByOrgAndPlanDateRange`.

### suffix.company.product.component.implementation.services
This will contain the concrete implementation of the service interfaces.

Think of service implementation classes as owners of the business logic.  We should be able to build a command line application that uses these service implementations instead of REST application.
No error flags in return methods, this is not Golang. If there is an error, throw an exception.
All persistence logic should be delegated to Dao class that belongs in the dal package.

<span style="color:red">Absolutely no IO classes or Rest resource classes should leak into this layer</span>.

### suffix.company.product.component.implementation.configuration

This package should contain Spring Boot External Configuration classes

Please refer [Spring Boot External Configuration documentation](https://docs.spring.io/spring-boot/docs/2.1.9.RELEASE/reference/html/boot-features-external-config.html) for more detailed information.

Each top level class in here should contain an @Component and @ConfigurationProperties reference.</p<p>DO NOT USE @Value annotations because they do not support relaxed binding.

For example, the following YAML

```yaml
acme:
remoteAddress: 255.255.255.255
security:
      username: John Doe
      password: supersecretpassword
```

Could use the following configuration.

```java
@Component
@ConfigurationProperties(prefix="acme")
@Getter
@Setter
public class AcmeConfiguration {

   private InetAddress remoteAddress;
   private Security security;

   @Getter
   @Setter
   public static class Security {
      private String username;
      private String password;
   }
}
```
## References
1. Bass L, Clements P, Kazman R. Software Architecture in Practice. Addison-Wesley Professional; 2012.

# Features
## Kibana
To enable Kibana logging just activate the Kibana profile through the SPRING_PROFILES_ACTIVE environment variable.
```
SPRING_PROFILES_ACTIVE=kibana
```