/**
 * <p>This package should contain Spring Boot External Configuration classes.
 * Please refer <a href="https://docs.spring.io/spring-boot/docs/2.1.9.RELEASE/reference/html/boot-features-external-config.html">
 *     Spring Boot External Configuration documentation</a> for more detailed information.</p>
 * <p>Each top level class in here should contain an @Component and @ConfigurationProperties reference.</p>
 * <p>DO NOT USE @Value annotations because they do not support relaxed binding.</p>
 * <p>For example, the following YAML</p>
 * <pre>{@code
 * acme:
 *    remoteAddress: 255.255.255.255
 *    security:
 *       username: John Doe
 *       password: supersecretpassword
 * }</pre>
 * <p>Could use the following configuration.</p>
 * <pre>{@code
 * @Component
 * @ConfigurationProperties(prefix="acme")
 * @Getter
 * @Setter
 * public class AcmeConfiguration {
 *    private InetAddress remoteAddress;
 *    private Security security;
 *    @Getter
 *    @Setter
 *    public static class Security {
 *       private String username;
 *       private String password;
 *    }
 * }
 * }</pre>
 */
package suffix.company.product.component.implementation.configurations;
