package cn.sparrowmini.org.model.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.sparrowmini.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.Group;
import cn.sparrowmini.org.model.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_group_organization")
public class GroupOrganization extends AbstractSparrowEntity {

	private static final long serialVersionUID = 1L;
	@EqualsAndHashCode.Include
	@EmbeddedId
	@Audited
	private GroupOrganizationPK id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "group_id", insertable = false, updatable = false)
	private Group group;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "organization_id", insertable = false, updatable = false)
	private Organization organization;

	public GroupOrganization(GroupOrganizationPK f) {
		this.id = f;
	}

	public GroupOrganization(String groupId, String organizationId) {
		this.id = new GroupOrganizationPK(groupId, organizationId);
	}
	
	@Embeddable
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GroupOrganizationPK implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Column(name = "group_id")
		private String groupId;
		@Column(name = "organization_id")
		private String organizationId;

	}

}
