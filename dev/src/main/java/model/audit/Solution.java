package model.audit;

import java.util.UUID;

public class Solution {
    private final String idSolution;
    private String type;
    private String idResponsible;

    // Default Constructor
    public Solution() {
        this.idSolution = UUID.randomUUID().toString();
        this.type = "correction";
        this.idResponsible = "unknown";
    }

    // Parameterized Constructor
    public Solution(String type, String idResponsible) {
        this.idSolution = UUID.randomUUID().toString();
        this.type = type;
        this.idResponsible = idResponsible;
    }

    // Getters and Setters
    public String getIdSolution() {
        return idSolution;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdResponsible() {
        return idResponsible;
    }

    public void setIdResponsible(String idResponsible) {
        this.idResponsible = idResponsible;
    }

    @Override
    public String toString() {
        return "ClauseSolution{" +
                "idSolution='" + idSolution + '\'' +
                ", type='" + type + '\'' +
                ", idResponsible='" + idResponsible + '\'' +
                '}';
    }
}
