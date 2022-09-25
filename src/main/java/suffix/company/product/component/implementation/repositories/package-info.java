/**
 * <p>This will contain concrete Data-Access objects used by the Services.</p>
 * <p>
 *     Distinguish different implementations by prefixing them with the type of Repository, for example
 *     JdbcTimeSeriesRepository and CassandraTimeSeriesRepository which both implement some Repository interface.
 * </p>
 * <p>These should provide CRUD operations, thus, we should use the following names:</p>
 * <p>createOne, createMany, updateOne, updateMany, removeById</p>
 * <p>Use find methods instead of get.  Make sure to use By and And. For example:  findTimeSeriesById, findTimeSeriesByOrgAndPlanDateRange</p>
 */
package suffix.company.product.component.implementation.repositories;
