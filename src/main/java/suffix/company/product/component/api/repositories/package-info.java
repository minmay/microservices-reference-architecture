/**
 * Contains interfaces for each Data-Access Object (aka Data Repositories). This is a persistence layer.
 * <p/>
 * Since we might eventually have two implementations, please prefix them with the type of Repository,
 * for example {@code  JdbcTimeSeriesRepository} and {@code CassandraTimeSeriesRepository} which both implement a repository interface.
 * <p/>
 * These should provide CRUD operations, thus, we should use the following names:<br/>
 * {@code  createOne}, {@code  createMany}, {@code  updateOne}, {@code  updateMany}, {@code  removeById}
 * Use find methods instead of get.  Make sure to use By and And. For example:  findTimeSeriesById, findTimeSeriesByOrgAndPlanDateRange
 * <p/>
 * Note that repositories should only reference entity objects. There absolutely should not be any
 * transfer objects from the io packaage or domain objects referenced within a repository.
 */
package suffix.company.product.component.api.repositories;
