package cn.sparrowmini.org.model.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.ValidationException;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.sparrowmini.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spr_employee_relation")
public class EmployeeRelation extends AbstractSparrowEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@EmbeddedId
	@Audited
	private EmployeeRelationPK id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "employee_id", insertable = false, updatable = false)
	private Employee employee;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "parent_id	", insertable = false, updatable = false)
	private Employee parent;

	public EmployeeRelation(EmployeeRelationPK f) {
		this.id = f;
	}

	public EmployeeRelation(String employeeId, String parentId) {
		this.id = new EmployeeRelationPK(employeeId, parentId);
	}

	@PrePersist
	@PreUpdate
	private void preSave() {
		if (id.getEmployeeId().equals(id.getParentId())) {
			throw new ValidationException("can not add relation to self");
		}
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Embeddable
	public static class EmployeeRelationPK implements Serializable {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		@Column(name = "employee_id")
		private String employeeId;
		@Column(name = "parent_id")
		private String parentId;

	}

}
