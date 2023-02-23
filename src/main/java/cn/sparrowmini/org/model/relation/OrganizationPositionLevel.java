package cn.sparrowmini.org.model.relation;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.sparrowmini.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.Organization;
import cn.sparrowmini.org.model.PositionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_organization_position_level")
public class OrganizationPositionLevel extends AbstractSparrowEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EqualsAndHashCode.Include
	@EmbeddedId
	@Audited
	private OrganizationPositionLevelPK id;
	@Audited
	private String stat;

//	@Transient
//	@JsonProperty
//	private long childCount;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumns({ @JoinColumn(name = "organization_id", referencedColumnName = "organization_id"),
			@JoinColumn(name = "position_level_id", referencedColumnName = "position_level_id") })
	private List<EmployeeOrganizationLevel> employeeOrganizationLevels;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "position_level_id", insertable = false, updatable = false)
	private PositionLevel positionLevel;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "organization_id", insertable = false, updatable = false)
	private Organization organization;

	public OrganizationPositionLevel(OrganizationPositionLevelPK f) {
		this.id = f;
	}

	public OrganizationPositionLevel(String organizationId, String positionLevelId) {
		this.id = new OrganizationPositionLevelPK(organizationId, positionLevelId);
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class OrganizationPositionLevelPK implements Serializable {

		private static final long serialVersionUID = 1L;
		@Column(name = "organization_id")
		private String organizationId;
		@Column(name = "position_level_id")
		private String positionLevelId;
	}

}
