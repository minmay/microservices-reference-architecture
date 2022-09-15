/**
 * <p>
 * This will contain any POJOs that are used in the HTTP Request / Response.
 * We use POJOs here because they are easily serialized with Jackson.
 * I expect each supported HTTP Method to have its own Request and Response POJO.
 * </p>
 * <h3>Controller IO Naming Conventions</h3>
 * <p>
 * <ol>
 *     <li>Use the suffix Request for requests, and Response for responses.</li>
 *     <li>Use the word Find, Create, Remove in an HTTP GET, DELETE respectively, but use the HTTP method name for other objects.</li>
 *     <li>Use the word One for singular, and Many for plural.</li>
 * </ol>
 * </p>
 * <h3>Controller IO Naming Convention Examples</h3>
 * <p>
 * <table>
 *     <tr>
 *         <td>Method</td>
 *         <td>Resource</td>
 *         <td>Request Object</td>
 *         <td>Response Object</td>
 *     </tr>
 *     <tr>
 *         <td>GET</td>
 *         <td>/pluralNouns</td>
 *         <td>N/A -uses path and query parameters.</td>
 *         <td>FindManyPluralNounsResponse</td>
 *     </tr>
 *     <tr>
 *         <td>GET</td>
 *         <td>/pluralNouns/{id}</td>
 *         <td>N/A -uses path and query parameters.</td>
 *         <td>FindOnePluralNoun</td>
 *     </tr>
 *     <tr>
 *         <td>PUT</td>
 *         <td>/pluralNouns</td>
 *         <td>PutOnePluralNounRequest</td>
 *         <td>PutOnePluralNounResponse</td>
 *     </tr>
 *     <tr>
 *         <td><POST/td>
 *         <td>/pluralNouns</td>
 *         <td>PostOnePluralNounRequest</td>
 *         <td>PostOnePluralNounResponse</td>
 *     </tr>
 *     <tr>
 *         <td>DELETE</td>
 *         <td>/pluralNouns/{id}</td>
 *         <td>N/A</td>
 *         <td>RemoveOnePluralNounResponse (realistically, this will have no response).</td>
 *     </tr>
 * </table>
 * </p>
 */
package suffix.company.product.component.implementation.controllers.io;