package edu.wakehealth.dr.ddi.model.geo;

import org.nutz.dao.entity.annotation.Table;

@Table("GEO_Link")
public class GEO_Link {
	private Integer OriginalId;

	private Integer TargetId;

	public Integer getOriginalId() {
		return OriginalId;
	}

	public GEO_Link setOriginalId(Integer originalId) {
		OriginalId = originalId;
		return this;
	}

	public Integer getTargetId() {
		return TargetId;
	}

	public GEO_Link setTargetId(Integer targetId) {
		TargetId = targetId;
		return this;
	}

}