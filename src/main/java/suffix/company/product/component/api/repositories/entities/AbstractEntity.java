package suffix.company.product.component.api.repositories.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Parent class for all entities.
 */
@Getter
@Setter
@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public abstract class AbstractEntity implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ToString.Include
	private Long id;

	/**
	 * Implementation of equals and hashcode where there are no natural business keys, taken from Vlad Mihalcea's
	 * <a href=https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier>blog</a>
	 * with minor changes.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null) {
			return false;
		}

		if (!getClass().getCanonicalName().equals(o.getClass().getCanonicalName())) {
			return false;
		}

		AbstractEntity other = (AbstractEntity) o;

		return getId() != null && getId().equals(other.getId());
	}

	public AbstractEntity(Long id) {
		this.id = id;
	}

	/**
	 * Implementation of equals and hashcode where there are no natural business keys, taken from Vlad Mihalcea's
	 * <a href=https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier>blog</a>.
	 */
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public boolean isNew() {
		return id == null;
	}
}
