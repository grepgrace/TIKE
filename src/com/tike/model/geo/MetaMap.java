package com.tike.model.geo;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.entity.annotation.*;

import com.tike.utils.Tools;



@Table("MetaMap")
public class MetaMap {
	@Id
	private int Time;
	@Readonly
	private String Result;
	private String ID;
	private String MM;
	private String Score;
	private double ScoreValue;
	private String UMLSConceptPrefer;
	private String UMLSConceptUniqu;
	private String SemanticTypeList;
	private String TriggerInformation;
	private String Location;
	private String PositionalInformation;
	private String Treecode;
	private String LocText;
	private int GEOId;

	public String getID() {
		return ID;
	}

	public MetaMap setID(String iD) {
		ID = iD;
		return this;
	}

	public String getMM() {
		return MM;
	}

	public MetaMap setMM(String mM) {
		MM = mM;
		return this;
	}

	public String getScore() {
		return Score;
	}

	public MetaMap setScore(String score) {
		Score = score;
		return this;
	}

	public String getUMLSConceptPrefer() {
		return UMLSConceptPrefer;
	}

	public MetaMap setUMLSConceptPrefer(String uMLSConceptPrefer) {
		UMLSConceptPrefer = uMLSConceptPrefer;
		return this;
	}

	public String getUMLSConceptUniqu() {
		return UMLSConceptUniqu;
	}

	public MetaMap setUMLSConceptUniqu(String uMLSConceptUniqu) {
		UMLSConceptUniqu = uMLSConceptUniqu;
		return this;
	}

	public String getSemanticTypeList() {
		return SemanticTypeList;
	}

	public MetaMap setSemanticTypeList(String semanticTypeList) {
		SemanticTypeList = semanticTypeList;
		return this;
	}

	public String getTriggerInformation() {
		return TriggerInformation;
	}

	public MetaMap setTriggerInformation(String triggerInformation) {
		TriggerInformation = triggerInformation;
		return this;
	}

	public String getLocation() {
		return Location;
	}

	public MetaMap setLocation(String location) {
		Location = location;
		return this;
	}

	public String getPositionalInformation() {
		return PositionalInformation;
	}

	public MetaMap setPositionalInformation(String positionalInformation) {
		PositionalInformation = positionalInformation;
		return this;
	}

	public String getTreecode() {
		return Treecode;
	}

	public MetaMap setTreecode(String treecode) {
		Treecode = treecode;
		return this;
	}

	public String getResult() {
		return Result;
	}

	public MetaMap setResult(String result) {
		Result = result;
		return this;
	}

	public int getTime() {
		return Time;
	}

	public MetaMap setTime(int time) {
		Time = time;
		return this;
	}

	public static MetaMap getInstance(String result) {
		if (!(result != null && result.indexOf("|") > -1 && result.split("\\|").length >= 8))
			return null;
		else {
			MetaMap map = new MetaMap();
			map.Result = result;
			String[] vs = result.split("\\|");
			map.ID = vs[0];
			map.MM = vs[1];
			map.Score = vs[2];
			if (Tools.isDouble(map.Score))
				map.ScoreValue = Double.valueOf(vs[2]);
			map.UMLSConceptPrefer = vs[3];
			map.UMLSConceptUniqu = vs[4];
			map.SemanticTypeList = vs[5];
			map.TriggerInformation = vs[6];
			map.Location = vs[7];
			if (vs.length > 8)
				map.PositionalInformation = vs[8];
			if (vs.length > 9)
			map.Treecode = vs[9];
			return map;
		}
	}

	public static List<MetaMap> getList(String results) {
		String[] lines = results.split("\r\n");
		List<MetaMap> list=new ArrayList<MetaMap>();
		for (String result : lines) {
			if (!(result != null && result.indexOf("|") > -1 && result.split("\\|").length >= 8))
				continue;
			MetaMap map = MetaMap.getInstance(result);
			if (map != null)
				list.add(map);
		}
		return list;
	}

	public String getLocText() {
		return LocText;
	}

	public MetaMap setLocText(String locText) {
		LocText = locText;
		return this;
	}

	public int getGEOId() {
		return GEOId;
	}

	public MetaMap setGEOId(int gEOId) {
		GEOId = gEOId;
		return this;
	}

	public double getScoreValue() {
		return ScoreValue;
	}

	public MetaMap setScoreValue(double scoreValue) {
		ScoreValue = scoreValue;
		return this;
	}

}