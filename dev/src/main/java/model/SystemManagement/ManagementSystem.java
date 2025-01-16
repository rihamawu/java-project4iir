package model.SystemManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.SystemManagement.Standard.Standard;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class ManagementSystem {
    private String idManagementSystem;
    private String idOrganization; // ID of the organization to which this system belongs
    private String description;
    private String certificate;
    private ArrayList<Standard> standards = new ArrayList<>();
    private ArrayList<Requirement> requirements = new ArrayList<>();

    public void setIdManagementSystem(String idManagementSystem) {
        this.idManagementSystem = idManagementSystem;
    }

    // Constructors
    public ManagementSystem() {
        this.idManagementSystem = UUID.randomUUID().toString();
        this.idOrganization = "unknown"; // Default value
        this.description = "unknown";
        this.certificate = "unknown";
    }

    public ManagementSystem(String idManagementSystem, String idOrganization, String description, String certificate, ArrayList<Standard> standards, ArrayList<Requirement> requirements) {
        this.idManagementSystem = idManagementSystem;
        this.idOrganization = idOrganization;
        this.description = description;
        this.certificate = certificate;
        this.standards = standards;
        this.requirements = requirements;
    }


    // Getters and Setters
    public String getIdManagementSystem() {
        return idManagementSystem;
    }

    public String getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public ArrayList<Standard> getStandards() {
        return standards;
    }

    public void setStandards(ArrayList<Standard> standards) {
        this.standards = standards;
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<Requirement> requirements) {
        this.requirements = requirements;
    }

    // Method to update the ManagementSystem object
    public void editManagementSystem(ManagementSystem updatedManagementSystem) {
        if (updatedManagementSystem != null) {
            this.setIdOrganization(updatedManagementSystem.getIdOrganization());
            this.setDescription(updatedManagementSystem.getDescription());
            this.setCertificate(updatedManagementSystem.getCertificate());
        }
    }
    // Method to get a Standard by ID
    @JsonIgnore
    public Standard getStandardById(String idStandard) {
        Optional<Standard> standard = standards.stream()
                .filter(std -> std.getIdStandard().equals(idStandard))
                .findFirst();
        return standard.orElse(null);
    }

    // Method to create a new Standard
    public void createStandard(Standard newStandard) {
        if (newStandard != null) {
            standards.add(newStandard);
            System.out.println("New Standard added successfully.");
        }
    }

    // Method to edit a Standard by ID
    public Boolean editStandard(String idStandard, Standard updatedStandard) {
        Optional<Standard> standardOptional = standards.stream()
                .filter(std -> std.getIdStandard().equals(idStandard))
                .findFirst();

        if (standardOptional.isPresent()) {
            standardOptional.get().editStandard(updatedStandard);
            System.out.println("The standard is updated.");
            return true;
        } else {
            System.out.println("Standard not found.");
            return false;
        }
    }

    // Method to delete a Standard by ID
    public Boolean deleteStandard(String idStandard) {
        Optional<Standard> standardOptional = standards.stream()
                .filter(std -> std.getIdStandard().equals(idStandard))
                .findFirst();

        if (standardOptional.isPresent()) {
            standards.remove(standardOptional.get());
            System.out.println("Standard deleted successfully.");
            return true;
        } else {
            System.out.println("Standard not found.");
            return false;
        }
    }

    // Method to create a new Requirement
    @JsonIgnore
    public Standard getRequirementById(String idStandard) {
        Optional<Standard> standard = standards.stream()
                .filter(std -> std.getIdStandard().equals(idStandard))
                .findFirst();
        return standard.orElse(null);
    }
    public void createRequirement(Requirement newRequirement) {
        if (newRequirement != null) {
            requirements.add(newRequirement);
            System.out.println("New Requirement added successfully.");
        }
    }

    // Method to edit a Requirement by ID
    public Boolean editRequirement(String idOtherRequirement, Requirement updatedRequirement) {
        Optional<Requirement> otherRequirementOptional = requirements.stream()
                .filter(req -> req.getIdRequirement().equals(idOtherRequirement))
                .findFirst();

        if (otherRequirementOptional.isPresent()) {
            otherRequirementOptional.get().editRequirement(updatedRequirement);
            System.out.println("The Requirement is updated.");
            return true;
        } else {
            System.out.println("Requirement not found.");
            return false;
        }
    }

    // Method to delete a Requirement by ID
    public Boolean deleteRequirement(String idOtherRequirement) {
        Optional<Requirement> otherRequirementOptional = requirements.stream()
                .filter(req -> req.getIdRequirement().equals(idOtherRequirement))
                .findFirst();

        if (otherRequirementOptional.isPresent()) {
            requirements.remove(otherRequirementOptional.get());
            System.out.println("Requirement deleted successfully.");
            return true;
        } else {
            System.out.println("Requirement not found.");
            return false;
        }
    }


    @Override
    public String toString() {
        return "ManagementSystem{" +
                "idManagementSystem='" + idManagementSystem + '\'' +
                ", idOrg='" + idOrganization + '\'' +
                ", description='" + description + '\'' +
                ", certificate='" + certificate + '\'' +
                ", standards=" + standards +
                ", otherRequirements=" + requirements +
                '}';
    }
}