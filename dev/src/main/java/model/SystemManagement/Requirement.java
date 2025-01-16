package model.SystemManagement;

import java.util.UUID;

public class Requirement {
    private String idRequirement;
    private String idManagementSystem;
    private String idOrganization;
    private String description;
    private String reference;
    private String name;

    public Requirement() {
        this.idRequirement = UUID.randomUUID().toString();
        this.description = "Unknown";
        this.name = "Unknown";
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    public Requirement(String idOrganization,String idManagementSystem, String description, String name, String reference) {
        this.idOrganization=idOrganization;
        this.idManagementSystem=idManagementSystem;

        this.idRequirement = UUID.randomUUID().toString();
        this.description = description;
        this.name = name;
        this.reference = reference;
    }

    public void editRequirement(Requirement updatedRequirement){
        this.setName(updatedRequirement.getName());
        this.setDescription(updatedRequirement.getDescription());
        this.setReference(updatedRequirement.getReference());
    }

    public String getIdRequirement() {
        return idRequirement;
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

    public String getIdManagementSystem() {
        return idManagementSystem;
    }

    public void setIdManagementSystem(String idManagementSystem) {
        this.idManagementSystem = idManagementSystem;
    }

    public String getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }

    @Override
    public String toString() {
        return "Requirement{" +
                ", idManagementSystem='" + idManagementSystem + '\'' +
                ", idOrganization='" + idOrganization + '\'' +
                "idRequirement='" + idRequirement + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", reference=" + reference +

                '}';
    }
}
