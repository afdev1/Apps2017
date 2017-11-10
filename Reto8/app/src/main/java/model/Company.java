package model;

import java.util.HashMap;

public class Company {
    private Long comId;
    private String name;
    private String url;
    private Integer telephone;
    private String email;
    private Integer type;

    public static HashMap<Integer, String> types = new HashMap<Integer, String>(){{
        put(1, "Consultoría");
        put(2, "Desarrollo a la medida");
        put(3, "Fábrica de software");
    }};

    public Company(){
    }

    public Company(Long comId, String name, String url, Integer telephone, String email, Integer type) {
        this.comId = comId;
        this.name = name;
        this.url = url;
        this.telephone = telephone;
        this.email = email;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Company{" +
                "comId=" + comId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", telephone=" + telephone +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }
}
