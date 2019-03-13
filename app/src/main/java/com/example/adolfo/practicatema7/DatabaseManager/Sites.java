package com.example.adolfo.practicatema7.DatabaseManager;

public class Sites {
    private Long idSite;
    private String nameSite;
    private Double latitudeSite;
    private Double longitudeSite;
    private String commentSite;
    private Float ratingSite;
    private Integer categorySite;

    public Sites ()
    {
        this.idSite=null;
        this.nameSite="";
        this.latitudeSite=null;
        this.longitudeSite=null;
        this.commentSite="";
        this.ratingSite=null;
        this.categorySite=0;
    }

    public Sites(Long idSite, String nameSite, Double latitudeSite, Double longitudeSite, String commentSite, Float ratingSite, Integer categorySite) {
        this.idSite = idSite;
        this.nameSite = nameSite;
        this.latitudeSite = latitudeSite;
        this.longitudeSite = longitudeSite;
        this.commentSite = commentSite;
        this.ratingSite = ratingSite;
        this.categorySite = categorySite;
    }

    public Long getIdSite() {
        return idSite;
    }

    public void setIdSite(Long idSite) {
        this.idSite = idSite;
    }

    public String getNameSite() {
        return nameSite;
    }

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public Double getLatitudeSite() {
        return latitudeSite;
    }

    public void setLatitudeSite(Double latitudeSite) {
        this.latitudeSite = latitudeSite;
    }

    public Double getLongitudeSite() {
        return longitudeSite;
    }

    public void setLongitudeSite(Double longitudeSite) {
        this.longitudeSite = longitudeSite;
    }

    public String getCommentSite() {
        return commentSite;
    }

    public void setCommentSite(String commentSite) {
        this.commentSite = commentSite;
    }

    public Float getRatingSite() {
        return ratingSite;
    }

    public void setRatingSite(Float ratingSite) {
        this.ratingSite = ratingSite;
    }

    public Integer getCategorySite() {
        return categorySite;
    }

    public void setCategorySite(Integer categorySite) {
        this.categorySite = categorySite;
    }

    @Override
    public String toString() {
        return "Sites{" +
                "idSite=" + idSite +
                ", nameSite='" + nameSite + '\'' +
                ", latitudeSite=" + latitudeSite +
                ", longitudeSite=" + longitudeSite +
                ", commentSite='" + commentSite + '\'' +
                ", ratingSite=" + ratingSite +
                ", categorySite=" + categorySite +
                '}';
    }
}
