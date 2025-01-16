package model.SystemManagement.Standard;

import java.util.UUID;

public class Clause {
    private String idClause;
    private String idManagementSystem;
    private String idOrganization;
    private String idStandard;
    private String name;
    private String description;
    private String reference;

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

    public String getIdStandard() {
        return idStandard;
    }

    public void setIdStandard(String idStandard) {
        this.idStandard = idStandard;
    }

    public Clause() {
        this.idClause = UUID.randomUUID().toString();
        this.idManagementSystem = "unknown";
        this.idOrganization = "unknown";
        this.idStandard = "unknown";
        this.name = "Unknown";
        this.description = "Unknown";
        this.reference = "Unknown";
    }

    public Clause(String idOrganization , String idManagementSystem , String idStandard, String name, String description, String reference) {
      this.idOrganization = idOrganization;
      this.idManagementSystem = idManagementSystem;
      this.idStandard = idStandard;
        this.idClause = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.reference = reference;

    }

    public void editClause(Clause updatedClause) {

        this.setName(updatedClause.getName());
        this.setDescription(updatedClause.getDescription());
        this.setIdOrganization(updatedClause.getIdOrganization());
        this.setIdManagementSystem(updatedClause.getIdManagementSystem());
        this.setIdStandard(updatedClause.getIdStandard());
        this.setReference(updatedClause.getReference());


    }

    public String getIdClause() {
        return idClause;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    @Override
    public String toString() {
        return "Clause{" +
                "idClause='" + idClause + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reference='" + reference + '\'' +

                '}';
    }
}
