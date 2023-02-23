package cn.sparrowmini.org.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sparrowmini.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.relation.GroupRole;
import cn.sparrowmini.org.model.relation.OrganizationRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Audited
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_role")
public class Role extends AbstractSparrowEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GenericGenerator(name = "id-generator", strategy = "uuid")
	@GeneratedValue(generator = "id-generator")
	@Audited
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String id;

	@Column(unique = true)
	private String code;
	private String name;
	private Boolean isRoot;
	private String stat;

	// use for create relation at batch
//	@Transient
//	@JsonProperty
//	private Set<OrganizationRolePK> parentIds;

	// the role belong to organization
//	@Transient
//	@JsonProperty
//	private Set<String> organizationIds;

	@NotAudited
	@JsonIgnore
	@OneToMany(targetEntity = OrganizationRole.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<OrganizationRole> organizationRoles;

	@NotAudited
	@JsonIgnore
	@OneToMany(targetEntity = GroupRole.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<GroupRole> groupRoles;

	public Role(String name, String code) {
		this.name = name;
		this.code = code;
	}

	@PrePersist
	private void preSave() {
		if (isRoot == null) {
			isRoot = true;
		}
	}

}