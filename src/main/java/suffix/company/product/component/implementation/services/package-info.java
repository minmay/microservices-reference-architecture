/**
 * <p>This will contain the concrete implementation of the service interfaces.</p>
 * <p>Think of service implementation classes as owners of the business logic.  We should be able to build a command line
 * application that uses these service implementations instead of REST application.
 * There should be no error flags in return methods, this is not Golang. If there is an error, throw an exception.
 * All persistence logic should be delegated to Dao class that belongs in the dal package.</p>
 * <p>Absolutely no IO classes or Rest resource classes should leak into this layer.
 * Contains interfaces for each service.</p>
 * <p>The Business Logic Services Layer receives a domain object from the Presentation Layer, and provides business
 * functionality. It will use the Client Interface to communicate to other components in a system or the
 * Persistence Layer to persist data.</p>
 */
package suffix.company.product.component.implementation.services;
