package model.Organization;

import java.util.UUID;

public class OrgProcess {
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
    public void editProcess( OrgProcess updatedOrgProcess){

         this.idOrganization = updatedOrgProcess.getIdOrganization();
         this.name = updatedOrgProcess.getName();
         this.description = updatedOrgProcess.getDescription();
         this.idOrgProcess = updatedOrgProcess.getIdOrgProcess();


    }

    public OrgProcess(String idOrganization, String idOrgProcess, String name, String description) {
        this.idOrganization = idOrganization;
        this.idOrgProcess = idOrgProcess;
        this.name = name;
        this.description = description;
    }

    public OrgProcess() {
        this.idOrgProcess = UUID.randomUUID().toString();
        this.idOrganization = "unknown";
        this.name = "Unknown";
        this.description = "Unknown";
    }


    public OrgProcess(String idOrganization, String name, String description) {
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
