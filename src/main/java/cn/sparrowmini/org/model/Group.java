package cn.sparrowmini.org.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sparrowmini.org.model.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.constant.GroupTypeEnum;
import cn.sparrowmini.org.model.relation.GroupEmployee;
import cn.sparrowmini.org.model.relation.GroupOrganization;
import cn.sparrowmini.org.model.relation.GroupPositionLevel;
import cn.sparrowmini.org.model.relation.GroupRelation;
import cn.sparrowmini.org.model.relation.GroupRole;
import cn.sparrowmini.org.model.relation.OrganizationGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_group")
public class Group extends AbstractSparrowEntity {

	/**
	 * 
	 */
	@EqualsAndHashCode.Include
	@Id
	@GenericGenerator(name = "id-generator", strategy = "uuid")
	@GeneratedValue(generator = "id-generator")
	@Audited
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String id;
	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	@Audited
	private String code;
	@Audited
	private String name;
	@Audited
	private String owner;
	@Audited
	private String stat;
	@Audited
	private Boolean isRoot;
	@Audited
	@Enumerated
	private GroupTypeEnum type;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "group")
	private Set<OrganizationGroup> organizationGroups;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "parent")
	private Set<GroupRelation> members;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "group")
	private Set<GroupEmployee> groupEmployees;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "group")
	private Set<GroupOrganization> groupOrganizations;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "group")
	private Set<GroupRole> groupRoles;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "group")
	private Set<GroupPositionLevel> groupLevels;

//	@JsonIgnore
//	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "group")
//	private Set<GroupSysrole> groupSysroles;

	public Group(String name, String code) {
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