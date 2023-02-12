package cn.sparrowmini.org.model.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.sparrowmini.org.model.Employee;
import cn.sparrowmini.org.model.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.relation.OrganizationRole.OrganizationRolePK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_employee_organization_role")
@NamedQueries({
		@NamedQuery(name = "EmployeeOrganizationRole.findByEmployeeId", query = "SELECT o FROM EmployeeOrganizationRole o WHERE o.id.employeeId=: employeeId") })
public class EmployeeOrganizationRole extends AbstractSparrowEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EqualsAndHashCode.Include
	@EmbeddedId
	@Audited
	private EmployeeOrganizationRolePK id;

	
	private String stat;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({
			@JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
			@JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false) })
	private OrganizationRole organizationRole;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "employee_id", insertable = false, updatable = false)
	private Employee employee;

	public EmployeeOrganizationRole(EmployeeOrganizationRolePK f) {
		this.id = f;
	}

	public EmployeeOrganizationRole(String employeeId, OrganizationRolePK f) {
		this.id = new EmployeeOrganizationRolePK(f, employeeId);
	}
	
	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class EmployeeOrganizationRolePK implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private OrganizationRolePK organizationRoleId;

		@Column(name = "employee_id")
		private String employeeId;

	}

}