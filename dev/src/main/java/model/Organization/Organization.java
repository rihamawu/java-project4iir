package model.Organization;

import model.managementSystem.ManagementSystem;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class Organization {
    private String idOrganization;
    private String name;
    private String description;
    private ArrayList<Site> sites = new ArrayList<>();
    private ArrayList<ManagementSystem> managementSystems = new ArrayList<>();
    private ArrayList<OrganizationProcess> organizationProcesses = new ArrayList<>();

    // Constructors
    public Organization() {
        this.idOrganization = UUID.randomUUID().toString();
        this.name = "unknown";
        this.description = "unknown";
    }

    public Organization( String name, String description) {
        this.idOrganization = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }
    public Organization( String idOrganization,String name, String description) {
        this.idOrganization = idOrganization;
        this.name = name;
        this.description = description;   this.idOrganization = idOrganization;
    }

    // Getters
    public ArrayList<OrganizationProcess> getOrgProcesses() {
        return organizationProcesses;
    }

    // Setters
    public void setOrgProcesses(ArrayList<OrganizationProcess> organizationProcesses) {
        this.organizationProcesses = organizationProcesses;
    }

    public String getIdOrganization() {
        return idOrganization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty())
            this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Site> getSites() {
        return sites;
    }

    public void setSites(ArrayList<Site> sites) {
        this.sites = sites;
    }

    public ArrayList<ManagementSystem> getManagementSystems() {
        return managementSystems;
    }

    public void setManagementSystems(ArrayList<ManagementSystem> ManagementSystems) {
        this.managementSystems = ManagementSystems;
    }

    // Method to update the Organization object
    public void editOrganization(Organization updatedOrganization) {
        this.setName(updatedOrganization.name);
        this.setDescription(updatedOrganization.description);
    }

    // Method to add a Site
    public void addSite(Site site) {
        if (site != null) {
            this.sites.add(site);
        }
    }

    // Method to edit a Site by ID
    public boolean editSite(String idSite, Site updatedSite) {
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getIdSite().equals(idSite)) {
                // Replace the site at the same index
                sites.get(i).editSite(updatedSite);
                return true;
            }
        }
        return false;
    }

    // Method to delete a Site by ID
    public boolean deleteSite(String siteId) {
        Optional<Site> siteToDelete = sites.stream()
                .filter(site -> site.getIdSite().equals(siteId))
                .findFirst();

        if (siteToDelete.isPresent()) {
            sites.remove(siteToDelete.get());
            return true;
        }
        return false;
    }

    // Method to find a Site by ID
    public Site findSiteById(String siteId) {
        return sites.stream()
                .filter(site -> site.getIdSite().equals(siteId))
                .findFirst()
                .orElse(null);
    }

    // Method to add a ManagementSystem
    public void addSystem(ManagementSystem system) {
        if (system != null) {
            this.managementSystems.add(system);
        }
    }

    // Method to edit a ManagementSystem by ID
    public boolean editSystem(String idManagementSystem, ManagementSystem updatedSystem) {
        for (int i = 0; i < managementSystems.size(); i++) {
            if (managementSystems.get(i).getIdManagementSystem().equals(idManagementSystem)) {
                // Replace the system at the same index
                managementSystems.get(i).editManagementSystem(updatedSystem);
                return true;
            }
        }
        return false;
    }

    // Method to delete a ManagementSystem by ID
    public boolean deleteSystem(String systemId) {
        Optional<ManagementSystem> systemToDelete = managementSystems.stream()
                .filter(system -> system.getIdManagementSystem().equals(systemId))
                .findFirst();

        if (systemToDelete.isPresent()) {
            managementSystems.remove(systemToDelete.get());
            return true;
        }
        return false;
    }

    // Method to find a ManagementSystem by ID
    public ManagementSystem findSystemById(String systemId) {
        return managementSystems.stream()
                .filter(system -> system.getIdManagementSystem().equals(systemId))
                .findFirst()
                .orElse(null);
    }

    // Method to add a Process
    public void addOrgProcess(OrganizationProcess organizationProcess) {
        if (organizationProcess != null) {
            this.organizationProcesses.add(organizationProcess);
        }
    }

    // Method to edit a Process by ID
    public boolean editOrgProcess(String idProcess, OrganizationProcess updatedOrganizationProcess) {
        for (int i = 0; i < organizationProcesses.size(); i++) {
            if (organizationProcesses.get(i).getIdOrgProcess().equals(idProcess)) {
                // Replace the process at the same index
                organizationProcesses.get(i).editProcess(updatedOrganizationProcess);
                return true;
            }
        }
        return false;
    }

    // Method to delete a Process by ID
    public boolean deleteOrgProcess(String processId) {
        Optional<OrganizationProcess> processToDelete = organizationProcesses.stream()
                .filter(orgProcess -> orgProcess.getIdOrgProcess().equals(processId))
                .findFirst();

        if (processToDelete.isPresent()) {
            organizationProcesses.remove(processToDelete.get());
            return true;
        }
        return false;
    }

    // Method to find a Process by ID
    public OrganizationProcess findProcessById(String processId) {
        return organizationProcesses.stream()
                .filter(orgProcess -> orgProcess.getIdOrgProcess().equals(processId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "idOrg='" + idOrganization + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sites=" + sites +
                ", systems=" + managementSystems +
                ", orgProcesses=" + organizationProcesses +
                '}';
    }
}