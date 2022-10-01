package sod.eastonone.elasticsearch.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    public Team(String id) {
		super();
		this.id = Integer.parseInt(id);
	}
    
    public Team() {
    	super();
    }

    private String name;
    
    private String description;
        
    private LocalDateTime createTime;
    
    private LocalDateTime modifyTime;

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", description=" + description + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + "]";
	}

}
