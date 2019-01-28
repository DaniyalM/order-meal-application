package structure.com.foodportal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Continent")
    @Expose
    private String continent;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("SurfaceArea")
    @Expose
    private Double surfaceArea;
    @SerializedName("IndepYear")
    @Expose
    private Integer indepYear;
    @SerializedName("Population")
    @Expose
    private Integer population;
    @SerializedName("LifeExpectancy")
    @Expose
    private Double lifeExpectancy;
    @SerializedName("GNP")
    @Expose
    private Double gNP;
    @SerializedName("GNPOld")
    @Expose
    private Object gNPOld;
    @SerializedName("LocalName")
    @Expose
    private String localName;
    @SerializedName("GovernmentForm")
    @Expose
    private String governmentForm;
    @SerializedName("HeadOfState")
    @Expose
    private String headOfState;
    @SerializedName("Capital")
    @Expose
    private Integer capital;
    @SerializedName("Code2")
    @Expose
    private String code2;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(Double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public Integer getIndepYear() {
        return indepYear;
    }

    public void setIndepYear(Integer indepYear) {
        this.indepYear = indepYear;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(Double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public Double getgNP() {
        return gNP;
    }

    public void setgNP(Double gNP) {
        this.gNP = gNP;
    }

    public Object getgNPOld() {
        return gNPOld;
    }

    public void setgNPOld(Object gNPOld) {
        this.gNPOld = gNPOld;
    }

    public Object getGNPOld() {
        return gNPOld;
    }

    public void setGNPOld(Object gNPOld) {
        this.gNPOld = gNPOld;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

}
