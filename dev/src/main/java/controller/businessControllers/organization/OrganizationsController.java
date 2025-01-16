package controller.businessControllers.organization;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Organization.OrgProcess;
import model.Organization.Organization;
import model.Organization.Site;
import model.SystemManagement.ManagementSystem;
import model.SystemManagement.Requirement;
import model.SystemManagement.Standard.Clause;
import model.SystemManagement.Standard.Standard;
import utils.JsonFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrganizationsController {
    private static final String ORGANIZATION_FILE_PATH = JsonFileHandler.ORGANIZATION_FILE_PATH;
    private static ArrayList<Organization> organizations = new ArrayList<>();


    public OrganizationsController() {
        loadOrganizations();
        System.out.println("Organizations loaded successfully: " + organizations);
    }

    // Load organizations from the JSON file
    public void loadOrganizations() {
        try {
            List<Organization> loadedOrganizations = JsonFileHandler.loadData(ORGANIZATION_FILE_PATH, new TypeReference<List<Organization>>() {
            });
            organizations = new ArrayList<>(loadedOrganizations);
            System.out.println(organizations.size() + " organizations loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading organizations: " + e.getMessage());
        }
    }

    // Save organizations to the JSON file
    public void saveOrganizations() {
        try {
            JsonFileHandler.saveData(ORGANIZATION_FILE_PATH, organizations);
            System.out.println("Organizations saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving organizations: " + e.getMessage());
        }
    }


    public void createOrganization(Organization organizationData) {
        organizations.add(organizationData);
        System.out.println("New Organization added successfully.");

        saveOrganizations();
    }

    // Add or edit an organization
    public boolean editOrganization(String id, Organization updatedOrganization) {
        Optional<Organization> existingOrganization = organizations.stream()
                .filter(org -> org.getIdOrganization().equals(id))
                .findFirst();

        if (existingOrganization.isPresent()) {
            existingOrganization.get().editOrganization(updatedOrganization);
            int index = organizations.indexOf(existingOrganization.get());
            organizations.get(index).editOrganization(updatedOrganization);
            System.out.println("Organization updated successfully.");
            saveOrganizations();
            return true;
        } else {
            System.out.println("Organization not found.");
            return false;
        }


    }

    // Delete an organization by ID
    public boolean deleteOrganization(String idOrg) {
        Optional<Organization> organizationToDelete = organizations.stream()
                .filter(org -> org.getIdOrganization().equals(idOrg))
                .findFirst();

        if (organizationToDelete.isPresent()) {
            organizations.remove(organizationToDelete.get());
            saveOrganizations();
            System.out.println("Organization deleted successfully.");
            return true;
        } else {
            System.out.println("Organization not found.");
            return false;
        }
    }

    // Get an organization by ID
    public Organization getOrganizationById(String idOrg) {
        Optional<Organization> organization = organizations.stream()
                .filter(org -> org.getIdOrganization().equals(idOrg))
                .findFirst();
        return organization.orElse(null);
    }

    // Method to get a ManagementSystem by ID
    public ManagementSystem getManagementSystemById(String idOrg, String idManagementSystem) throws Exception {
        Organization organization = getOrganizationById(idOrg);

        if (organization != null) {
            ManagementSystem managementSystem = organization.findSystemById(idManagementSystem);
            if (managementSystem != null) {
                return managementSystem;
            } else {
                throw new Exception("Management System with ID " + idManagementSystem + " not found in organization " + idOrg + ".");
            }
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

    // Get all organizations
    public List<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    // Add a management system to an organization
    public void addManagementSystemToOrganization(String idOrg, ManagementSystem managementSystem) throws Exception {
        Organization organization = getOrganizationById(idOrg);
        if (organization != null) {
            organization.addSystem(managementSystem);
            saveOrganizations();
            System.out.println("Management System added to organization successfully.");
        } else {
            // Throw an exception if the organization is not found
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }


    // Method to get a ManagementSystem by ID
    public ManagementSystem getSystemManagementById(String idOrg, String idManagementSystem) throws Exception {
        System.out.println(idOrg);
        Organization organization = getOrganizationById(idOrg);

        if (organization != null) {
            ManagementSystem managementSystem = organization.findSystemById(idManagementSystem);
            if (managementSystem != null) {
                return managementSystem;
            } else {
                throw new Exception("Management System with ID " + idManagementSystem + " not found.");
            }
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

    // Method to get a Standard by ID
    public Standard getSystemManagementStandardById(String idOrg, String idManagementSystem, String idStandard) throws Exception {

        ManagementSystem managementSystem = getSystemManagementById(idOrg, idManagementSystem);

        if (managementSystem != null) {
            Standard standard = managementSystem.getStandardById(idStandard);
            if (standard != null) {
                return standard;
            } else {
                throw new Exception("Standard with ID " + idStandard + " not found.");
            }
        } else {
            throw new Exception("Management System with ID " + idManagementSystem + " not found.");
        }
    }

    // Method to create a new Standard
    public void createSystemManagementStandard(String idOrg, String idManagementSystem, Standard newStandard) throws Exception {
        ManagementSystem managementSystem = getSystemManagementById(idOrg, idManagementSystem);

        if (managementSystem != null) {
            managementSystem.createStandard(newStandard);
            saveOrganizations(); // Save changes to the JSON file
        } else {
            throw new Exception("Management System with ID " + idManagementSystem + " not found.");
        }
    }

    // Method to edit a Standard by ID
    public void editSystemManagementStandardById(String idOrg, String idManagementSystem, String idStandard, Standard updatedStandard) throws Exception {
        ManagementSystem managementSystem = getSystemManagementById(idOrg, idManagementSystem);

        if (managementSystem != null) {
            boolean isUpdated = managementSystem.editStandard(idStandard, updatedStandard);
            if (!isUpdated) {
                throw new Exception("Standard with ID " + idStandard + " not found.");
            }
            saveOrganizations(); // Save changes to the JSON file
        } else {
            throw new Exception("Management System with ID " + idManagementSystem + " not found.");
        }
    }

    // Method to delete a Standard by ID
    public void deleteSystemManagementStandardById(String idOrg, String idManagementSystem, String idStandard) throws Exception {
        ManagementSystem managementSystem = getSystemManagementById(idOrg, idManagementSystem);

        if (managementSystem != null) {
            boolean isDeleted = managementSystem.deleteStandard(idStandard);
            if (!isDeleted) {
                throw new Exception("Standard with ID " + idStandard + " not found.");
            }
            saveOrganizations(); // Save changes to the JSON file
        } else {
            throw new Exception("Management System with ID " + idManagementSystem + " not found.");
        }
    }

    // Get all clauses across all standards in all management systems in all organizations
    public List<Clause> getAllClauses() {
        List<Clause> allClauses = new ArrayList<>();
        for (Organization organization : organizations) {
            for (ManagementSystem managementSystem : organization.getManagementSystems()) {
                for (Standard standard : managementSystem.getStandards()) {
                    allClauses.addAll(standard.getClauses());
                }
            }
        }
        return allClauses;
    }

    // Method to create a new Clause
    public void createSystemManagementStandardClause(String idOrg, String idManagementSystem, String idStandard, Clause newClause) throws Exception {
        Standard standard = getSystemManagementStandardById(idOrg, idManagementSystem, idStandard);

        if (standard != null) {
            standard.createClause(newClause);
            saveOrganizations(); // Save changes to the JSON file
        } else {
            throw new Exception("Standard with ID " + idStandard + " not found.");
        }
    }

    // Method to edit a Clause by ID
    public void editSystemManagementStandardClauseById(String idOrg, String idManagementSystem, String idStandard, String idClause, Clause updatedClause) throws Exception {
        Standard standard = getSystemManagementStandardById(idOrg, idManagementSystem, idStandard);

        if (standard != null) {
            boolean isUpdated = standard.editClause(idClause, updatedClause);
            if (!isUpdated) {
                throw new Exception("Clause with ID " + idClause + " not found.");
            }
            saveOrganizations(); // Save changes to the JSON file
        } else {
            throw new Exception("Standard with ID " + idStandard + " not found.");
        }
    }

    // Method to delete a Clause by ID
    public void deleteSystemManagementStandardClauseById(String idOrg, String idManagementSystem, String idStandard, String idClause) throws Exception {
        Standard standard = getSystemManagementStandardById(idOrg, idManagementSystem, idStandard);

        if (standard != null) {
            boolean isDeleted = standard.deleteClause(idClause);
            if (!isDeleted) {
                throw new Exception("Clause with ID " + idClause + " not found.");
            }
            saveOrganizations(); // Save changes to the JSON file
        } else {
            throw new Exception("Standard with ID " + idStandard + " not found.");
        }
    }


    // Edit a management system in an organization
    public boolean editManagementSystemInOrganization(String idOrg, String idManagementSystem, ManagementSystem updatedSystem) throws Exception {
        Organization organization = getOrganizationById(idOrg);
        if (organization != null) {
            boolean result = organization.editSystem(idManagementSystem, updatedSystem);
            if (result) {
                saveOrganizations();
                System.out.println("Management System updated successfully.");
            } else {
                throw new Exception("Management System not found." + idManagementSystem + " not found.");
            }
            return result;
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

    // Delete a management system from an organization
    public boolean deleteManagementSystemFromOrganization(String idOrg, String idManagementSystem) throws Exception {
        Organization organization = getOrganizationById(idOrg);
        if (organization != null) {
            boolean result = organization.deleteSystem(idManagementSystem);
            if (result) {
                saveOrganizations();
                System.out.println("Management System deleted successfully.");
            } else {
                throw new Exception("Management System not found." + idManagementSystem + " not found.");
            }
            return result;
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }


    // Method to create a new Requirement
    public void createSystemManagementRequirement(String idOrg, String idManagementSystem, Requirement newRequirement) throws Exception {
        ManagementSystem managementSystem = getSystemManagementById(idOrg, idManagementSystem);

        if (managementSystem != null) {
            managementSystem.createRequirement(newRequirement);
            saveOrganizations(); // Save changes to the JSON file
        } else {
            throw new Exception("Management System with ID " + idManagementSystem + " not found.");
        }
    }

    // Method to edit a Requirement by ID
    public void editSystemManagementRequirementById(String idOrg, String idManagementSystem, String idRequirement, Requirement updatedRequirement) throws Exception {
        ManagementSystem managementSystem = getSystemManagementById(idOrg, idManagementSystem);

        if (managementSystem != null) {
            boolean isUpdated = managementSystem.editRequirement(idRequirement, updatedRequirement);
            if (!isUpdated) {
                throw new Exception("Requirement with ID " + idRequirement + " not found.");
            }
            saveOrganizations(); // Save changes to the JSON file
        } else {
            throw new Exception("Management System with ID " + idManagementSystem + " not found.");
        }
    }

    // Method to delete a Requirement by ID
    public void deleteSystemManagementRequirementById(String idOrg, String idManagementSystem, String idRequirement) throws Exception {
        System.out.println(idOrg);
        ManagementSystem managementSystem = getSystemManagementById(idOrg, idManagementSystem);

        if (managementSystem != null) {
            boolean isDeleted = managementSystem.deleteRequirement(idRequirement);
            if (!isDeleted) {
                throw new Exception("Requirement with ID " + idRequirement + " not found.");
            }
            saveOrganizations(); // Save changes to the JSON file
        } else {
            throw new Exception("Management System with ID " + idManagementSystem + " not found.");
        }
    }


    // Get all management systems across all organizations
    public List<ManagementSystem> getAllManagementSystems() {
        List<ManagementSystem> allManagementSystems = new ArrayList<>();
        for (Organization organization : organizations) {
            allManagementSystems.addAll(organization.getManagementSystems());
        }
        return allManagementSystems;
    }

    // Get all requirements across all management systems in all organizations
    public List<Requirement> getAllRequirements() {
        List<Requirement> allRequirements = new ArrayList<>();
        for (Organization organization : organizations) {
            for (ManagementSystem managementSystem : organization.getManagementSystems()) {
                allRequirements.addAll(managementSystem.getRequirements());
            }
        }
        return allRequirements;
    }

    // Get all standards across all management systems in all organizations
    public List<Standard> getAllStandards() {
        List<Standard> allStandards = new ArrayList<>();
        for (Organization organization : organizations) {
            for (ManagementSystem managementSystem : organization.getManagementSystems()) {
                allStandards.addAll(managementSystem.getStandards());
            }
        }
        return allStandards;
    }


    // Get all sites across all organizations
    public List<Site> getAllSites() {
        List<Site> allSites = new ArrayList<>();
        for (Organization organization : organizations) {
            allSites.addAll(organization.getSites());
        }


        return allSites;
    }

    // Add a site to an organization
    public void addSiteToOrganization(String idOrg, Site site) throws Exception {
        Organization organization = getOrganizationById(idOrg);
        if (organization != null) {
            organization.addSite(site);
            saveOrganizations();
            System.out.println("Site added to organization successfully.");
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

    // Edit a site in an organization
    public boolean editSiteInOrganization(String idOrg, String idSite, Site updatedSite) throws Exception {
        Organization organization = getOrganizationById(idOrg);
        if (organization != null) {
            boolean result = organization.editSite(idSite, updatedSite);
            if (result) {
                saveOrganizations();
                System.out.println("Site updated successfully.");
            } else {
                throw new Exception("Site not found. ID: " + idSite);
            }
            return result;
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

    // Create a site in an organization
    public void createSiteInOrganization(String idOrg, Site site) throws Exception {
        Organization organization = getOrganizationById(idOrg);
        if (organization != null) {
            organization.addSite(site);
            saveOrganizations();
            System.out.println("Site created successfully.");
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

    // Delete a site from an organization
    public boolean deleteSiteFromOrganization(String idOrg, String idSite) throws Exception {
        Organization organization = getOrganizationById(idOrg);
        if (organization != null) {
            boolean result = organization.deleteSite(idSite);
            if (result) {
                saveOrganizations();
                System.out.println("Site deleted successfully.");
            } else {
                throw new Exception("Site not found. ID: " + idSite);
            }
            return result;
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

    // Get all processes across all organizations
    public List<OrgProcess> getAllProcesses() {
        List<OrgProcess> allProcesses = new ArrayList<>();
        for (Organization organization : organizations) {
            allProcesses.addAll(organization.getOrgProcesses());
        }
        return allProcesses;
    }

    // Method to create a new Process
    public void createProcess(String idOrg, OrgProcess newProcess) throws Exception {
        Organization organization = getOrganizationById(idOrg);

        if (organization != null) {
            organization.addOrgProcess(newProcess);
            saveOrganizations(); // Save changes to the JSON file
            System.out.println("the OrgProcess added to organization successfully.");
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

    // Method to edit a Process by ID
    public void editOrgProcess(String idOrg, String idOrgProcess, OrgProcess updatedOrgProcess) throws Exception {
        Organization organization = getOrganizationById(idOrg);

        if (organization != null) {
            boolean isUpdated = organization.editOrgProcess(idOrgProcess, updatedOrgProcess);
            if (!isUpdated) {
                throw new Exception("Process with ID " + updatedOrgProcess + " not found.");
            }
            saveOrganizations(); // Save changes to the JSON file
            System.out.println("Process updated successfully.");
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

    // Method to delete a Process by ID
    public void deleteProcess(String idOrg, String idProcess) throws Exception {
        Organization organization = getOrganizationById(idOrg);

        if (organization != null) {
            boolean isDeleted = organization.deleteOrgProcess(idProcess);
            if (!isDeleted) {
                throw new Exception("Process with ID " + idProcess + " not found.");
            }
            saveOrganizations(); // Save changes to the JSON file
            System.out.println("Process deleted successfully.");
        } else {
            throw new Exception("Organization with ID " + idOrg + " not found.");
        }
    }

}