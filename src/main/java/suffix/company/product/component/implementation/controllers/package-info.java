/**
 * This will contain all the @RestController classes. Each method will typically follow these steps:
 * <ol>
 * 		<li>Transform IO Request Transfer Object POJO into one or more domain objects.</li>
 * 		<li>Call the appropriate Service implementation that contains the business logic.</li>
 * 		<li>Transform the domain object into the appropriate IO Response Transfer Object POJO.</li>
 * 		<li>Respond with the proper HTTP Code.</li>
 * </ol>
 * Rest Resources Coding Conventions
 *<ul>
 * <li>Resources are an HTTP paths that accept verbs.  The only verbs that it accepts are
 * POST, PUT, GET, DELETE, OPTIONS, and PATCH.
 * PUT, GET, and DELETE are idempotent operations.
 * For this reasons, POST, PUT, GET, DELETE usually correspond with create, update, get, delete, respectively.</li>
 * <li>DO NOT USE verb names for rest resources, use nouns instead. Sometimes it is necessary to rename a verb into a noun (e.g. instead of executePlan, use planExecutions).</li>
 * <li>NEVER return true/false flags to indicate something succeeded or failed. Instead, use the correct HTTP response codes (you can review them at this reference).</li>
 * <li>Absolutely no business logic nor persistence logic will exist in this layer. Business Logic will be delegated to Service layer.</li>
 * <li>GET operations should correspond to find method names. Do not use the term get in controller method names because in Java get should be used for properties.</li>
 * <li>POST operations should correspond to create method names.</li>
 * <li>PUT operations should correspond to update method names.</li>
 * <li>DELETE operations should correspond to remove method names.</li>
 * <li>If a method retrieves or updates many, please use the word <b>many</b> in the method name, for example findManyByOrganization.
 * You can also use the term all if your method retrieves all, such as findAll.</li>
 * <li>If the method updates only one entity, then please use the word <b>one</b> in the method name, for example findOneById.</li>
 * </ul>
 * <p>This is a presentation tier layer.</p>
 * <p>A well-designed architecture could easily switch Presentation Layer implementations with different technologies.
 * Perhaps this could be a Swing Application, Command Line Application, or MVC framework with server side templating.
 * This particular example documents a Layered REST application.</p>
 */
package suffix.company.product.component.implementation.controllers;