package edu.wakehealth.dr.ddi.model.geo;

import org.nutz.dao.entity.annotation.Id;

public class Geo_gds_item {
	@Id()
	private Integer id;
	private Integer gdsid;

    private String name;
	private Integer parentId;
	private String parentname;

    private String type;

    private String itemcontent;

    public Integer getGdsid() {
        return gdsid;
    }

	public Geo_gds_item setGdsid(Integer gdsid) {
		this.gdsid = gdsid;
		return this;
    }

    public String getName() {
        return name;
    }

	public Geo_gds_item setName(String name) {
        this.name = name == null ? null : name.trim();
		return this;
    }

    public String getType() {
        return type;
    }

	public Geo_gds_item setType(String type) {
        this.type = type == null ? null : type.trim();
		return this;
    }

    public String getItemcontent() {
        return itemcontent;
    }

	public Geo_gds_item setItemcontent(String itemcontent) {
        this.itemcontent = itemcontent == null ? null : itemcontent.trim();
		return this;
    }

	public Integer getParentId() {
		return parentId;
	}

	public Geo_gds_item setParentId(Integer parentId) {
		this.parentId = parentId;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public Geo_gds_item setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getParentname() {
		return parentname;
	}

	public Geo_gds_item setParentname(String parentname) {
		this.parentname = parentname;
		return this;
	}

}