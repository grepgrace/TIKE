package com.tike.model.geo;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

//Id Accession GDS title summary GPL GSE taxon entryType gdsType ptechType valType SSInfo subsetInfo PDAT 
//suppFile n_samples SeriesTitle PlatformTitle PlatformTaxa SamplesTaxa PubMedIds FTPLink GEO2R

@Table("GEO_Data")
public class GEO_Data implements java.io.Serializable {
	@Id(auto = false)
	private Integer Id;
	private String Accession;
	private String GDS;
	private String title;
	private String summary;
	private String GPL;
	private String GSE;
	private String GSM;
	private String Relations;
	private String taxon;
	private String entryType;
	private String gdsType;
	private String ptechType;
	private String valType;
	private String SSInfo;
	private String subsetInfo;
	private String PDAT;
	private String suppFile;
	private Integer n_samples;
	private String SeriesTitle;
	private String PlatformTitle;
	private String PlatformTaxa;
	private String SamplesTaxa;
	private String PubMedIds;
	private String FTPLink;
	private String GEO2R;
	private boolean isUpdated;

	public Integer getId() {
		return Id;
	}

	public GEO_Data setId(Integer id) {
		Id = id;
		return this;
	}

	public String getAccession() {
		return Accession;
	}

	public GEO_Data setAccession(String accession) {
		Accession = accession;
		return this;
	}

	public String getGDS() {
		return GDS;
	}

	public GEO_Data setGDS(String gDS) {
		GDS = gDS;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public GEO_Data setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getSummary() {
		return summary;
	}

	public GEO_Data setSummary(String summary) {
		this.summary = summary;
		return this;
	}

	public String getGPL() {
		return GPL;
	}

	public GEO_Data setGPL(String gPL) {
		GPL = gPL;
		return this;
	}

	public String getGSE() {
		return GSE;
	}

	public GEO_Data setGSE(String gSE) {
		GSE = gSE;
		return this;
	}

	public String getTaxon() {
		return taxon;
	}

	public GEO_Data setTaxon(String taxon) {
		this.taxon = taxon;
		return this;
	}

	public String getEntryType() {
		return entryType;
	}

	public GEO_Data setEntryType(String entryType) {
		this.entryType = entryType;
		return this;
	}

	public String getGdsType() {
		return gdsType;
	}

	public GEO_Data setGdsType(String gdsType) {
		this.gdsType = gdsType;
		return this;
	}

	public String getPtechType() {
		return ptechType;
	}

	public GEO_Data setPtechType(String ptechType) {
		this.ptechType = ptechType;
		return this;
	}

	public String getValType() {
		return valType;
	}

	public GEO_Data setValType(String valType) {
		this.valType = valType;
		return this;
	}

	public String getSSInfo() {
		return SSInfo;
	}

	public GEO_Data setSSInfo(String sSInfo) {
		SSInfo = sSInfo;
		return this;
	}

	public String getSubsetInfo() {
		return subsetInfo;
	}

	public GEO_Data setSubsetInfo(String subsetInfo) {
		this.subsetInfo = subsetInfo;
		return this;
	}

	public String getPDAT() {
		return PDAT;
	}

	public GEO_Data setPDAT(String pDAT) {
		PDAT = pDAT;
		return this;
	}

	public String getSuppFile() {
		return suppFile;
	}

	public GEO_Data setSuppFile(String suppFile) {
		this.suppFile = suppFile;
		return this;
	}

	public Integer getN_samples() {
		return n_samples;
	}

	public GEO_Data setN_samples(Integer n_samples) {
		this.n_samples = n_samples;
		return this;
	}

	public String getSeriesTitle() {
		return SeriesTitle;
	}

	public GEO_Data setSeriesTitle(String seriesTitle) {
		SeriesTitle = seriesTitle;
		return this;
	}

	public String getPlatformTitle() {
		return PlatformTitle;
	}

	public GEO_Data setPlatformTitle(String platformTitle) {
		PlatformTitle = platformTitle;
		return this;
	}

	public String getPlatformTaxa() {
		return PlatformTaxa;
	}

	public GEO_Data setPlatformTaxa(String platformTaxa) {
		PlatformTaxa = platformTaxa;
		return this;
	}

	public String getSamplesTaxa() {
		return SamplesTaxa;
	}

	public GEO_Data setSamplesTaxa(String samplesTaxa) {
		SamplesTaxa = samplesTaxa;
		return this;
	}

	public String getPubMedIds() {
		return PubMedIds;
	}

	public GEO_Data setPubMedIds(String pubMedIds) {
		PubMedIds = pubMedIds;
		return this;
	}

	public String getFTPLink() {
		return FTPLink;
	}

	public GEO_Data setFTPLink(String fTPLink) {
		FTPLink = fTPLink;
		return this;
	}

	public String getGEO2R() {
		return GEO2R;
	}

	public GEO_Data setGEO2R(String gEO2R) {
		GEO2R = gEO2R;
		return this;
	}

	public String getGSM() {
		return GSM;
	}

	public GEO_Data setGSM(String gSM) {
		GSM = gSM;
		return this;
	}

	public String getRelations() {
		return Relations;
	}

	public GEO_Data setRelations(String relations) {
		Relations = relations;
		return this;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public GEO_Data setUpdated(boolean isUpdate) {
		this.isUpdated = isUpdate;
		return this;
	}




}