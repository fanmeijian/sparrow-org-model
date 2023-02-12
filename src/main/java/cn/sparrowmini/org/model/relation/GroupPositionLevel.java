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
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.sparrowmini.org.model.Group;
import cn.sparrowmini.org.model.PositionLevel;
import cn.sparrowmini.org.model.common.AbstractSparrowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "spr_group_position_level")
public class GroupPositionLevel extends AbstractSparrowEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EqualsAndHashCode.Include
	@EmbeddedId
	@Audited
	@JoinColumns({ @JoinColumn(name = "group_id"), @JoinColumn(name = "position_level_id") })
	private GroupPositionLevelPK id;
	@Audited
	private String stat;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id", insertable = false, updatable = false)
	private Group group;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "position_level_id", insertable = false, updatable = false)
	private PositionLevel positionLevel;

	public GroupPositionLevel(GroupPositionLevelPK f) {
		this.id = f;
	}

	public GroupPositionLevel(String groupId, String positionLevelId) {
		this.id = new GroupPositionLevelPK(groupId, positionLevelId);
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Embeddable
	public static class GroupPositionLevelPK implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Column(name = "group_id")
		private String groupId;
		@Column(name = "position_level_id")
		private String positionLevelId;

	}

}
