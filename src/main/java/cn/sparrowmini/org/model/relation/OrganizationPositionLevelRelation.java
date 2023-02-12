package cn.sparrowmini.org.model.relation;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.ValidationException;

import org.hibernate.envers.Audited;

import cn.sparrowmini.org.model.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.relation.OrganizationPositionLevel.OrganizationPositionLevelPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_organization_position_level_relation")
public class OrganizationPositionLevelRelation extends AbstractSparrowEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@EmbeddedId
	@Audited
	private OrganizationPositionLevelRelationPK id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({
			@JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
			@JoinColumn(name = "position_level_id", referencedColumnName = "position_level_id", insertable = false, updatable = false) })
	private OrganizationPositionLevel organizationLevel;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({
			@JoinColumn(name = "parent_organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
			@JoinColumn(name = "parent_position_level_id", referencedColumnName = "position_level_id", insertable = false, updatable = false) })
	private OrganizationPositionLevel parentOrganizationLevel;

	public OrganizationPositionLevelRelation(OrganizationPositionLevelPK id, OrganizationPositionLevelPK parentId) {
		this.id = new OrganizationPositionLevelRelationPK(id, parentId);
	}
	
	public OrganizationPositionLevelRelation(OrganizationPositionLevelRelationPK id) {
		this.id = id;
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
	public static class OrganizationPositionLevelRelationPK implements Serializable {
	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  @Embedded
	  private OrganizationPositionLevelPK id;

	  @Embedded
	  @AttributeOverrides({
	      @AttributeOverride(name = "organizationId", column = @Column(name = "parent_organization_id")),
	      @AttributeOverride(name = "positionLevelId", column = @Column(name = "parent_position_level_id"))})
	  private OrganizationPositionLevelPK parentId;
	}

}
