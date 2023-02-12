package cn.sparrowmini.org.model.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.sparrowmini.org.model.Employee;
import cn.sparrowmini.org.model.Group;
import cn.sparrowmini.org.model.common.AbstractSparrowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_group_employee")
@NamedQueries({
	@NamedQuery(name = "GroupEmployee.findByEmployeeId", query = "SELECT o FROM GroupEmployee o WHERE o.id.employeeId = :employeeId") })
public class GroupEmployee extends AbstractSparrowEntity {

	public GroupEmployee(GroupEmployeePK groupEmployeePK) {
		this.id = groupEmployeePK;
    }
	
	public GroupEmployee(String groupId, String employeeId) {
		this.id = new GroupEmployeePK(groupId, employeeId);
    }

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@EmbeddedId
	@Audited
	private GroupEmployeePK id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "group_id", insertable = false, updatable = false)
	private Group group;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "employee_id", insertable = false, updatable = false)
	private Employee employee;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable
	public static class GroupEmployeePK implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Column(name = "group_id")
		private String groupId;
		@Column(name = "employee_id")
		private String employeeId;
		
	}

}
