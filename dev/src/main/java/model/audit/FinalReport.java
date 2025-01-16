package model.audit;

import java.util.UUID;

public class FinalReport {
    private final String idRapport;
    private String url;

    // Default Constructor
    public FinalReport() {
        this.idRapport = UUID.randomUUID().toString();
        this.url = "unknown";
    }

    // Parameterized Constructor
    public FinalReport(String url) {
        this.idRapport = UUID.randomUUID().toString();
        this.url = url;
    }

    // Getters and Setters
    public String getIdRapport() {
        return idRapport;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FinalRapport{" +
                "idRapport='" + idRapport + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
