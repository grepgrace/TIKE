package edu.wakehealth.dr.ddi.model.geo;

import org.nutz.dao.entity.annotation.Id;

public class Geo_gds {
	@Id(auto = false)
	private Integer id;

    private String code;

    public Integer getId() {
        return id;
    }

	public Geo_gds setId(Integer id) {
		this.id = id;
		return this;
    }

    public String getCode() {
        return code;
    }

	public Geo_gds setCode(String code) {
		this.code = code == null ? null : code.trim();
		// this.code = "GDS" + id;
		return this;
    }
}