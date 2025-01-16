package model.audit;

import java.util.UUID;

public class StandardStat {
    private final String idStandardStat;
    private String passStat;
    private Solution solution;


    // Default Constructor
    public StandardStat() {
        this.idStandardStat = UUID.randomUUID().toString();
        this.passStat = "notYet";
        this.solution = new Solution();
    }


    // Parameterized Constructor


    public StandardStat(String idStandardStat, String passStat, Solution solution) {
        this.idStandardStat = idStandardStat;
        this.setPassStat(passStat);
        this.solution = solution;
    }

    public String getPassStat() {
        return passStat;
    }
    public Solution getSolution() {
        return solution;
    }
    // Getters and Setters
    public String getIdStandardStat() {
        return idStandardStat;
    }

    public void setPassStat(String passStat) {

        if(passStat.equals("notYet") || passStat.equals("Pass") || passStat.equals("fail") )  {
            this.passStat = passStat;
        }
        else {
            System.out.println("Invalid Pass Stat");
        }
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "StandardStat{" +
                "idStandard='" + idStandardStat +
                '}';
    }
}
