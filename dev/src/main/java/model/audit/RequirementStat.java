package model.audit;

import java.util.UUID;

public class RequirementStat {
    private final String idRequirementStat;
    private String passStat;
    private Solution solution;

    // Default Constructor
    public RequirementStat() {
        this.idRequirementStat = UUID.randomUUID().toString();
        this.passStat = "notYet";
        this.solution = new Solution();
    }

    // Parameterized Constructor
    public RequirementStat(String passStat, FinalReport clauseRapport, Solution solution) {
        this.idRequirementStat = UUID.randomUUID().toString();
        this.setPassStat(passStat);
        this.solution = solution;
    }

    // Getters and Setters
    public String getIdRequirementStat() {
        return idRequirementStat;
    }

    public String getPassStat() {
        return passStat;

    }

    public void setPassStat(String passStat) {
        if(passStat.equals("notYet") || passStat.equals("Pass") || passStat.equals("fail")  )  {
            this.passStat = passStat;
        }
        else {
            System.out.println("Invalid Pass Stat");
        }
    }



    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "ExigenceStat{" +
                "idExigence='" + idRequirementStat + '\'' +
                ", passStat='" + passStat + '\'' +
                ", Solution=" + solution +
                '}';
    }
}
