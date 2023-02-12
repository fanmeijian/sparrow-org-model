package cn.sparrowmini.org.model.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "spr_group_sysrole")
public class GroupSysrole extends AbstractSparrowEntity {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@EmbeddedId
	@Audited
	private GroupSysrolePK id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id", insertable = false, updatable = false)
	private Group group;

//  @JsonIgnore
//  @ManyToOne(fetch = FetchType.EAGER)
//  @JoinColumn(name = "sysrole_id", insertable = false, updatable = false)
//  private Sysrole sysrole;

	public GroupSysrole(GroupSysrolePK f) {
		this.id = f;
	}

	public GroupSysrole(String groupId, String sysroleId) {
		this.id = new GroupSysrolePK(groupId, sysroleId);
	}

	@Data
	@Embeddable
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GroupSysrolePK implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Column(name = "group_id")
		private String groupId;
		@Column(name = "sysrole_id")
		private String sysroleId;
	}

}
