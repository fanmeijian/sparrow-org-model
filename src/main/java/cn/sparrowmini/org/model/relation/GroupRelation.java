package cn.sparrowmini.org.model.relation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.ValidationException;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import cn.sparrowmini.common.AbstractSparrowEntity;
import cn.sparrowmini.org.model.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spr_group_relation")
public class GroupRelation extends AbstractSparrowEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Audited
	@EmbeddedId
	private GroupRelationPK id;
	
	@NotAudited
	@OneToOne
	@JoinColumn(name = "group_id", insertable = false, updatable = false)
	private Group group;
	
	@NotAudited
	@ManyToOne
	@JoinColumn(name = "parent_id", insertable = false, updatable = false)
	private Group parent;

	public GroupRelation(String groupId, String parentId) {
		this.id = new GroupRelationPK(groupId, parentId);
	}

	@PrePersist
	@PreUpdate
	private void preSave() {
		if (id.getGroupId().equals(id.getParentId())) {
			throw new ValidationException("can not add relation to self");
		}
	}
	
	@Embeddable
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GroupRelationPK implements Serializable{
	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  @Column(name = "group_id")
	  private String groupId;
	  @Column(name = "parent_id")
	  private String parentId;
	}

}
