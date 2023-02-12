package cn.sparrowmini.org.model.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import cn.sparrowmini.org.model.common.AbstractSparrowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "spr_group_user")
@Audited
public class GroupUser extends AbstractSparrowEntity {

	private static final long serialVersionUID = 1L;
	@EqualsAndHashCode.Include
	@EmbeddedId
	private GroupUserPK id;
	private String stat;

	public GroupUser(GroupUserPK f) {
		this.id = f;
	}

	public GroupUser(String groupId, String username) {
		this.id = new GroupUserPK(groupId, username);
	}

	@Embeddable
	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GroupUserPK implements Serializable {

		private static final long serialVersionUID = 1L;
		@Column(name = "group_id")
		private String groupId;
		private String username;

	}

}
