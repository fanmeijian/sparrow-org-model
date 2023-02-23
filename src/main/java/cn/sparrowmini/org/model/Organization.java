package cn.sparrowmini.org.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.sparrowmini.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.constant.OrganizationTypeEnum;
import cn.sparrowmini.org.model.relation.GroupOrganization;
import cn.sparrowmini.org.model.relation.OrganizationGroup;
import cn.sparrowmini.org.model.relation.OrganizationPositionLevel;
import cn.sparrowmini.org.model.relation.OrganizationRelation;
import cn.sparrowmini.org.model.relation.OrganizationRole;
import cn.sparrowmini.pem.model.common.ModelPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ModelPermission
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_organization")
@JsonIgnoreProperties(value={"children","dataPermissionToken"}, allowGetters=true)
public class Organization extends AbstractSparrowEntity {

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
	
	private static final long serialVersionUID = 8581950429388182649L;
	@Column(unique = true)
	@Audited
	@NotEmpty(message = "组织代码不能为空，且必须唯一！")
	private String code;
	@Audited
	@NotEmpty(message = "组织名称不能为空！")
	private String name;
	@Audited
	private String stat;
	@Audited
	private Boolean isRoot;
	// use for create relation at batch
//  @Transient
//  @JsonProperty
//  private List<String> parentIds;
	@Enumerated(EnumType.STRING)
	@Audited
	@NotNull(message = "组织类型不能为空！")
	private OrganizationTypeEnum type; // 公司还是部门

//	@Transient
//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
//	private long parentCount;
//
//	@Transient
//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
//	private long childCount;
//
//	@Transient
//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
//	private long levelCount;
//
//	@Transient
//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
//	private long groupCount;
//
//	@Transient
//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
//	private long roleCount;

//	@Transient
//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
//	private long employeeCount;

	@JsonIgnore
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<OrganizationRelation> children;

	@JsonIgnore
	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<OrganizationRelation> parents;

	@JsonIgnore
	@OneToMany(targetEntity = OrganizationRole.class, cascade = CascadeType.ALL, mappedBy = "organization", fetch = FetchType.LAZY)
	private Set<OrganizationRole> organizationRoles;

	@JsonIgnore
	@OneToMany(targetEntity = OrganizationPositionLevel.class, cascade = CascadeType.ALL, mappedBy = "organization", fetch = FetchType.LAZY)
	private Set<OrganizationPositionLevel> organizationLevels;

	@JsonIgnore
	@OneToMany(targetEntity = OrganizationGroup.class, cascade = CascadeType.ALL, mappedBy = "organization", fetch = FetchType.LAZY)
	private Set<OrganizationGroup> organizationGroups;

	@JsonIgnore
	@OneToMany(targetEntity = GroupOrganization.class, cascade = CascadeType.ALL, mappedBy = "organization", fetch = FetchType.LAZY)
	private Set<GroupOrganization> groupOrganizations;

	public Organization(String name, String code, OrganizationTypeEnum type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}
	
	@PrePersist
	private void preSave() {
		if (isRoot == null) {
			isRoot = true;
		}
	}
}
