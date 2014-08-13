package edu.wakehealth.dr.ddi.model.geo;

import org.nutz.dao.entity.annotation.Table;

@Table("GEO_Link_GDS2Key")
public class GEO_Link_GDS2Key {
	private Integer geoid;

	private Integer Keyid;
	private String KeyName;

	public Integer getGeoid() {
		return geoid;
	}

	public GEO_Link_GDS2Key setGeoid(Integer geoid) {
		this.geoid = geoid;
		return this;
	}

	public Integer getKeyid() {
		return Keyid;
	}

	public GEO_Link_GDS2Key setKeyid(Integer keyid) {
		Keyid = keyid;
		return this;
	}

	public String getKeyName() {
		return KeyName;
	}

	public GEO_Link_GDS2Key setKeyName(String keyName) {
		this.KeyName = keyName;
		return this;
	}


}