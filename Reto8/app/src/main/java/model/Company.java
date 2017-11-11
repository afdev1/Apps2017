package model;

public class Company {
    private Long comId;
    private String name;
    private String url;
    private Integer telephone;
    private String email;
    private String services;
    private String type;

    public Company(){
    }

    public Company(Long comId, String name, String url, Integer telephone, String email, String services, String type) {
        this.comId = comId;
        this.name = name;
        this.url = url;
        this.telephone = telephone;
        this.email = email;
        this.services = services;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + " - " + type;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
