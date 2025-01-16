package model.Organization;

import java.util.UUID;

public class OrganizationProcess {
    private String idOrgProcess;
    private String idOrganization;
    private String name;
    private String description;

    public String getIdOrganization() {
        return idOrganization;
    }
    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }
    public void editProcess( OrganizationProcess updatedOrganizationProcess){

         this.idOrganization = updatedOrganizationProcess.getIdOrganization();
         this.name = updatedOrganizationProcess.getName();
         this.description = updatedOrganizationProcess.getDescription();
         this.idOrgProcess = updatedOrganizationProcess.getIdOrgProcess();


    }

    public OrganizationProcess(String idOrganization, String idOrgProcess, String name, String description) {
        this.idOrganization = idOrganization;
        this.idOrgProcess = idOrgProcess;
        this.name = name;
        this.description = description;
    }

    public OrganizationProcess() {
        this.idOrgProcess = UUID.randomUUID().toString();
        this.idOrganization = "unknown";
        this.name = "Unknown";
        this.description = "Unknown";
    }


    public OrganizationProcess(String idOrganization, String name, String description) {
       this.idOrgProcess= UUID.randomUUID().toString();
        this.idOrganization= idOrganization;
        this.name = name;
        this.description = description;
    }

    public String getIdOrgProcess() {
        return idOrgProcess;
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

    @Override
    public String toString() {
        return "Process{" +
                "idProcess='" + idOrgProcess + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idOrgProcess='" + idOrgProcess + '\'' +
                '}';
    }
}
