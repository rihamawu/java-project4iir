package controller.businessControllers;


import com.fasterxml.jackson.core.type.TypeReference;
import model.Organization.Organization;
import model.audit.Audit;
import model.audit.RequirementStat;
import model.audit.StandardStat;
import utils.ControllersGetter;
import utils.JsonFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuditsController {
    private static final String AUDITS_FILE_PATH = JsonFileHandler.AUDITS_FILE_PATH;
    private static ArrayList<Audit> audits = new ArrayList<>();

    public AuditsController() {
        loadAudits();
        System.out.println("Audits loaded successfully: " + audits);
    }

    // Load audits from the JSON file
    public void loadAudits() {
        try {
            List<Audit> loadedAudits = JsonFileHandler.loadData(AUDITS_FILE_PATH, new TypeReference<List<Audit>>() {
            });
            audits = new ArrayList<>(loadedAudits);
            System.out.println(audits.size() + " audits loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading audits: " + e.getMessage());
        }
    }

    // Save audits to the JSON file
    public void saveAudits() {
        try {
            JsonFileHandler.saveData(AUDITS_FILE_PATH, audits);
            System.out.println("Audits saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving audits: " + e.getMessage());
        }
    }

    // Create a new audit
    public void createAudit(Audit auditData) throws Exception{
        ControllersGetter.organizationsController.getManagementSystemById(auditData.getIdOrganization(),auditData.getIdSystemManagement());
        ControllersGetter.accountsController.getAuditorById(auditData.getIdAuditor());
        audits.add(auditData);
        System.out.println("New Audit added successfully.");
        saveAudits();
    }

    // Edit an existing audit by ID
    public boolean editAudit(String id, Audit updatedAudit) throws Exception{
        ControllersGetter.organizationsController.getManagementSystemById(updatedAudit.getIdOrganization(),updatedAudit.getIdSystemManagement());
        ControllersGetter.accountsController.getAuditorById(updatedAudit.getIdAuditor());
        Optional<Audit> existingAudit = audits.stream()
                .filter(audit -> audit.getIdAudit().equals(id))
                .findFirst();

        if (existingAudit.isPresent()) {
            existingAudit.get().setDateDebut(updatedAudit.getDateDebut());
            existingAudit.get().setExpDate(updatedAudit.getExpDate());
            existingAudit.get().setSubject(updatedAudit.getSubject());
            existingAudit.get().setStatus(updatedAudit.getStatus());
            existingAudit.get().setIdAuditor(updatedAudit.getIdAuditor());
            existingAudit.get().setIdOrganization(updatedAudit.getIdOrganization());
            existingAudit.get().setIdSystemManagement(updatedAudit.getIdSystemManagement());
            existingAudit.get().setFinalReport(updatedAudit.getFinalReport());
            existingAudit.get().setTakeCertificate(updatedAudit.isTakeCertificate());
            existingAudit.get().setIsPass(updatedAudit.getIsPass());
            existingAudit.get().setStandardsStat(updatedAudit.getStandardsStat());
            existingAudit.get().setRequirementsStat(updatedAudit.getRequirementsStat());

            System.out.println("Audit updated successfully.");
            saveAudits();
            return true;
        } else {
            System.out.println("Audit not found.");
            return false;
        }
    }

    // Delete an audit by ID
    public boolean deleteAudit(String idAudit) {
        Optional<Audit> auditToDelete = audits.stream()
                .filter(audit -> audit.getIdAudit().equals(idAudit))
                .findFirst();

        if (auditToDelete.isPresent()) {
            audits.remove(auditToDelete.get());
            saveAudits();
            System.out.println("Audit deleted successfully.");
            return true;
        } else {
            System.out.println("Audit not found.");
            return false;
        }
    }

    // Get an audit by ID
    public Audit getAuditById(String idAudit) {
        Optional<Audit> audit = audits.stream()
                .filter(a -> a.getIdAudit().equals(idAudit))
                .findFirst();
        return audit.orElse(null);
    }

    // Get all audits
    public List<Audit> getAllAudits() {
        return new ArrayList<>(audits);
    }

    // Get all audits for a specific organization
    public List<Audit> getAuditsByOrganization(String idOrganization) {
        List<Audit> organizationAudits = new ArrayList<>();
        for (Audit audit : audits) {
            if (audit.getIdOrganization().equals(idOrganization)) {
                organizationAudits.add(audit);
            }
        }
        return organizationAudits;
    }

    // Get all audits for a specific management system
    public List<Audit> getAuditsByManagementSystem(String idSystemManagement) {
        List<Audit> managementSystemAudits = new ArrayList<>();
        for (Audit audit : audits) {
            if (audit.getIdSystemManagement().equals(idSystemManagement)) {
                managementSystemAudits.add(audit);
            }
        }
        return managementSystemAudits;
    }

    // Get all audits for a specific auditor
    public List<Audit> getAuditsByAuditor(String idAuditor) {
        List<Audit> auditorAudits = new ArrayList<>();
        for (Audit audit : audits) {
            if (audit.getIdAuditor().equals(idAuditor)) {
                auditorAudits.add(audit);
            }
        }
        return auditorAudits;
    }

    // Update the status of an audit
    public boolean updateAuditStatus(String idAudit, String status) {
        Audit audit = getAuditById(idAudit);
        if (audit != null) {
            audit.setStatus(status);
            saveAudits();
            System.out.println("Audit status updated successfully.");
            return true;
        } else {
            System.out.println("Audit not found.");
            return false;
        }
    }

    // Update the final report of an audit
    public boolean updateAuditFinalReport(String idAudit, String url) {
        Audit audit = getAuditById(idAudit);
        if (audit != null) {
            audit.getFinalReport().setUrl(url);
            saveAudits();
            System.out.println("Audit final report updated successfully.");
            return true;
        } else {
            System.out.println("Audit not found.");
            return false;
        }
    }

    // Update the pass status of an audit
    public boolean updateAuditPassStatus(String idAudit, String isPass) {
        Audit audit = getAuditById(idAudit);
        if (audit != null) {
            audit.setIsPass(isPass);
            saveAudits();
            System.out.println("Audit pass status updated successfully.");
            return true;
        } else {
            System.out.println("Audit not found.");
            return false;
        }
    }

    // Add a standard stat to an audit
    public boolean addStandardStatToAudit(String idAudit, StandardStat standardStat) {
        Audit audit = getAuditById(idAudit);
        if (audit != null) {
            audit.getStandardsStat().add(standardStat);
            saveAudits();
            System.out.println("Standard stat added to audit successfully.");
            return true;
        } else {
            System.out.println("Audit not found.");
            return false;
        }
    }

    // Add a requirement stat to an audit
    public boolean addRequirementStatToAudit(String idAudit, RequirementStat requirementStat) {
        Audit audit = getAuditById(idAudit);
        if (audit != null) {
            audit.getRequirementsStat().add(requirementStat);
            saveAudits();
            System.out.println("Requirement stat added to audit successfully.");
            return true;
        } else {
            System.out.println("Audit not found.");
            return false;
        }
    }
}