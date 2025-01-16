package model.audit;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Audit {
    private final String idAudit;
    private String dateDebut;
    private String expDate;
    private String subject;
    private String status;
    private String idAuditor;
    private String idOrganization;
    private String idSystemManagement;
    private RapportFinale rapportFinale;
    private String takeCertificate;
    private String isPass;
    private List<StandardStat> StandardsStat;
    private List<StatRequirement> RequirementsStat;

    // Default Constructor
    public Audit() {
        this.idAudit = UUID.randomUUID().toString();
        this.dateDebut = "unknown";
        this.expDate = "unknown";
        this.subject = "unknown";
        this.status = "pending";
        this.idAuditor = "unknown";
        this.idOrganization = "unknown";
        this.idSystemManagement = "unknown";
        this.rapportFinale = new RapportFinale();
        setTakeCertificate("no");
        this.isPass = "notYet";
        this.StandardsStat =  new ArrayList<StandardStat>();
        this.RequirementsStat = new ArrayList<StatRequirement>();
    }

    // Parameterized Constructor
    public Audit(String dateDebut, String ExpDate, String subject, String status, String idAuditor, String idOrganization,
                 String idSystemManagement, RapportFinale finalRapport, String takeCertificate, String isPass,
                 List<StandardStat> StandardsStat, List<StatRequirement> RequirementsStat) {
        this.idAudit = UUID.randomUUID().toString();
        this.dateDebut = dateDebut;
        this.expDate = ExpDate;
        this.subject = subject;
        setStatus(status);
        this.idAuditor = idAuditor;
        this.idOrganization = idOrganization;
        this.idSystemManagement = idSystemManagement;
        this.rapportFinale = finalRapport;
        setTakeCertificate(takeCertificate);
        this.isPass = isPass;
        this.StandardsStat = StandardsStat;
        this.RequirementsStat = RequirementsStat;
    }

    // Getters and Setters
    public String getIdAudit() {
        return idAudit;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String ExpDate) {
        this.expDate = ExpDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status.equals("pending") || status.equals("notYet") || status.equals("in progress") || status.equals("completed"))  {
            this.status = status;
        }
        else {
            System.out.println("Invalid status");
        }

    }

    public String getIdAuditor() {
        return idAuditor;
    }

    public void setIdAuditor(String idAuditor) {
        this.idAuditor = idAuditor;
    }

    public String getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getIdSystemManagement() {
        return idSystemManagement;
    }

    public void setIdSystemManagement(String idSystemManagement) {
        this.idSystemManagement = idSystemManagement;
    }

    public RapportFinale getFinalReport() {
        return rapportFinale;
    }

    public void setFinalReport(RapportFinale rapportFinale) {
        this.rapportFinale = rapportFinale;
    }

    public String isTakeCertificate() {
        return takeCertificate;
    }

    public String getTakeCertificate() {
        return takeCertificate;
    }

    public void setTakeCertificate(String takeCertificate) {
        if(takeCertificate.equals("yes"))
        this.takeCertificate = "yes";
        else this.takeCertificate = "no";
    }

    public String getIsPass() {

        return isPass;
    }

    public void setIsPass(String isPass) {
      if(isPass.equals("notYet") || isPass.equals("pass") || isPass.equals("fail"))  {
          this.isPass = isPass;
      }
      else {
          System.out.println("Invalid pass");
      }

    }

    public List<StandardStat> getStandardsStat() {
        return StandardsStat;
    }

    public void setStandardsStat(List<StandardStat> standardsStat) {
        this.StandardsStat = standardsStat;
    }

    public List<StatRequirement> getRequirementsStat() {
        return RequirementsStat;
    }

    public void setRequirementsStat(List<StatRequirement> requirementsStat) {
        this.RequirementsStat = requirementsStat;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "idAudit='" + idAudit + '\'' +
                ", dateDebut='" + dateDebut + '\'' +
                ", ExpDate='" + expDate + '\'' +
                ", intitule='" + subject + '\'' +
                ", status='" + status + '\'' +
                ", idAuditor='" + idAuditor + '\'' +
                ", idOrg='" + idOrganization + '\'' +
                ", idSystemManagement='" + idSystemManagement + '\'' +
                ", finalRapport=" + rapportFinale +
                ", takeCertificate=" + takeCertificate +
                ", isPass='" + isPass + '\'' +
                ", arrStandardStat=" + StandardsStat +
                ", arrExigenceStat=" + RequirementsStat +
                '}';
    }
}
