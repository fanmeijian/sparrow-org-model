package cn.sparrowmini.org.model.relation;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.ValidationException;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.sparrowmini.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.relation.OrganizationRole.OrganizationRolePK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_organization_role_relation")
public class OrganizationRoleRelation extends AbstractSparrowEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@EmbeddedId
	@Audited
	private OrganizationRoleRelationPK id;

	@JsonIgnore
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
			@JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false) })
	private OrganizationRole organizationRole;

	@JsonIgnore
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "parent_organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
			@JoinColumn(name = "parent_role_id", referencedColumnName = "role_id", insertable = false, updatable = false) })
	private OrganizationRole parentOrganizationRole;

	public OrganizationRoleRelation(OrganizationRoleRelationPK id) {
		this.id = id;
	}

	public OrganizationRoleRelation(OrganizationRolePK id, OrganizationRolePK parentId) {
		this.id = new OrganizationRoleRelationPK(id, parentId);
	}

	@PrePersist
	@PreUpdate
	private void preSave() {
		if (id.getId().equals(id.getParentId())) {
			throw new ValidationException("can not add relation to self");
		}
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Embeddable
	public static class OrganizationRoleRelationPK implements Serializable {
	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  @Embedded
	  private OrganizationRolePK id;

	  @Embedded
	  @AttributeOverrides({
	      @AttributeOverride(name = "organizationId", column = @Column(name = "parent_organization_id")),
	      @AttributeOverride(name = "roleId", column = @Column(name = "parent_role_id"))})
	  private OrganizationRolePK parentId;
	}


}
