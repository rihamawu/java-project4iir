package model.Organization;

import java.util.UUID;

public class Site {
    private String idSite;
    private String idOrganization;
    private String address;
    private String description;
    private String name;

    public void setIdSite(String idSite) {
        this.idSite = idSite;
    }

    public String getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }

    public Site() {
        this.idSite = UUID.randomUUID().toString();
        this.address = "unknown";
        this.description = "unknown";
        this.name = "unknown";
    }

    public Site(String idSite, String idOrganization, String address, String description, String name) {
        this.idSite = idSite;
        this.idOrganization = idOrganization;
        this.address = address;
        this.description = description;
        this.name = name;

    }
    public void editSite(Site updatedSite){
         this.setAddress(updatedSite.getAddress());
         this.setDescription(updatedSite.getDescription());
         this.setName(updatedSite.getName());
    }


    public String getIdSite() {
        return idSite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Site{" +
                "idSite='" + idSite + '\'' +
                "idOrg='" + idOrganization + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
