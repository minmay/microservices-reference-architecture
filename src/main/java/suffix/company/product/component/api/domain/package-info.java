/**
 * Contains concrete domain objects, model classes, or business objects. Note that this package will
 * not contain transfer objects or entity objects. The type of classes in this package will be an
 * object model that represents the domain of this component.
 * <p/>
 * The Domain / Model layer should attempt to provide an object-oriented model of your business.
 * It is good to implement business logic here, but keep in mind that business logic computations
 * often require external persistence, and it is anti-pattern to couple persistence technologies
 * with a domain object. These absolutely should be Plain Ordinary Java objects, and
 * depending on your threading model, perhaps immutable as well.
 */
package suffix.company.product.component.api.domain;