package model.SystemManagement.Standard;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class Standard {
    private String idStandard;
    private String idOrganization;
    private String idManagementSystem;
    private String name;
    private String description;
    private String reference;
    private ArrayList<Clause> clauses = new ArrayList<>();

    // Constructors
    public Standard() {
        this.idStandard = UUID.randomUUID().toString();
        this.name = "unknown";
        this.description = "unknown";
        this.reference = "unknown";
    }

    public Standard(String idOrganization, String idManagementSystem, String name, String description, String reference, ArrayList<Clause> clauses) {
        this.idOrganization = idOrganization;
        this.idManagementSystem = idManagementSystem;
        this.idStandard = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.reference = reference;
        this.clauses = clauses;
    }

    // Getters and Setters
    public String getIdStandard() {
        return idStandard;
    }

    public String getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getIdManagementSystem() {
        return idManagementSystem;
    }

    public void setIdManagementSystem(String idManagementSystem) {
        this.idManagementSystem = idManagementSystem;
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

    public ArrayList<Clause> getClauses() {
        return clauses;
    }

    public void setClauses(ArrayList<Clause> clauses) {
        this.clauses = clauses;
    }

    // Method to update the Standard object
    public void editStandard(Standard updatedStandard) {
        if (updatedStandard != null) {
            this.setName(updatedStandard.getName());
            this.setDescription(updatedStandard.getDescription());
            this.setReference(updatedStandard.getReference());
        }
    }

    // Method to create a new Clause
    public void createClause(Clause newClause) {
        if (newClause != null) {
            clauses.add(newClause);
            System.out.println("New Clause added successfully.");
        }
    }

    // Method to edit a Clause by ID
    public boolean editClause(String idClause, Clause updatedClause) {
        Optional<Clause> clauseOptional = clauses.stream()
                .filter(clause -> clause.getIdClause().equals(idClause))
                .findFirst();

        if (clauseOptional.isPresent()) {
            clauseOptional.get().editClause(updatedClause);
            System.out.println("Clause updated successfully.");
            return true;
        } else {
            System.out.println("Clause not found.");
            return false;
        }
    }

    // Method to delete a Clause by ID
    public boolean deleteClause(String idClause) {
        Optional<Clause> clauseOptional = clauses.stream()
                .filter(clause -> clause.getIdClause().equals(idClause))
                .findFirst();

        if (clauseOptional.isPresent()) {
            clauses.remove(clauseOptional.get());
            System.out.println("Clause deleted successfully.");
            return true;
        } else {
            System.out.println("Clause not found.");
            return false;
        }
    }

    // Method to get a Clause by ID
    public Clause getClauseById(String idClause) {
        Optional<Clause> clauseOptional = clauses.stream()
                .filter(clause -> clause.getIdClause().equals(idClause))
                .findFirst();

        return clauseOptional.orElse(null); // Return the Clause if found, otherwise return null
    }

    @Override
    public String toString() {
        return "Standard{" +
                "idStandard='" + idStandard + '\'' +
                ", idOrganization='" + idOrganization + '\'' +
                ", idManagementSystem='" + idManagementSystem + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reference='" + reference + '\'' +
                ", clauses=" + clauses +
                '}';
    }
}