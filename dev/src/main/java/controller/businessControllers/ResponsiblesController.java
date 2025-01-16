package controller.businessControllers;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Responsible;
import utils.JsonFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResponsiblesController {
    private static final String RESPONSIBLE_FILE_PATH = JsonFileHandler.RESPONSIBLE_FILE_PATH;
    private static ArrayList<Responsible> responsibles = new ArrayList<>();

    public ResponsiblesController() {
        loadResponsibles();
        System.out.println("Responsibles loaded successfully: " + responsibles);
    }

    // Load responsibles from the JSON file
    public void loadResponsibles() {
        try {
            List<Responsible> loadedResponsibles = JsonFileHandler.loadData(RESPONSIBLE_FILE_PATH, new TypeReference<List<Responsible>>() {});
            responsibles = new ArrayList<>(loadedResponsibles);
            System.out.println(responsibles.size() + " responsibles loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading responsibles: " + e.getMessage());
        }
    }

    // Save responsibles to the JSON file
    public void saveResponsibles() {
        try {
            JsonFileHandler.saveData(RESPONSIBLE_FILE_PATH, responsibles);
            System.out.println("Responsibles saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving responsibles: " + e.getMessage());
        }
    }

    // Create a new responsible
    public void createResponsible(Responsible responsibleData) {
        responsibles.add(responsibleData);
        System.out.println("New Responsible added successfully.");
        saveResponsibles();
    }

    // Edit an existing responsible by ID
    public boolean editResponsible(String id, Responsible updatedResponsible) {
        Optional<Responsible> existingResponsible = responsibles.stream()
                .filter(resp -> resp.getIdResponsible().equals(id))
                .findFirst();

        if (existingResponsible.isPresent()) {
            Responsible responsible = existingResponsible.get();
            responsible.setFirstName(updatedResponsible.getFirstName());
            responsible.setLastName(updatedResponsible.getLastName());
            responsible.setEmail(updatedResponsible.getEmail());
            responsible.setPhoneNumber(updatedResponsible.getPhoneNumber());
            responsible.setDomain(updatedResponsible.getDomain());
            saveResponsibles();
            System.out.println("Responsible updated successfully.");
            return true;
        } else {
            System.out.println("Responsible not found.");
            return false;
        }
    }

    // Delete a responsible by ID
    public boolean deleteResponsible(String idResponsible) {
        Optional<Responsible> responsibleToDelete = responsibles.stream()
                .filter(resp -> resp.getIdResponsible().equals(idResponsible))
                .findFirst();

        if (responsibleToDelete.isPresent()) {
            responsibles.remove(responsibleToDelete.get());
            saveResponsibles();
            System.out.println("Responsible deleted successfully.");
            return true;
        } else {
            System.out.println("Responsible not found.");
            return false;
        }
    }

    // Get a responsible by ID
    public Responsible getResponsibleById(String idResponsible) {
        Optional<Responsible> responsible = responsibles.stream()
                .filter(resp -> resp.getIdResponsible().equals(idResponsible))
                .findFirst();
        return responsible.orElse(null);
    }

    // Get all responsibles
    public List<Responsible> getAllResponsibles() {
        return new ArrayList<>(responsibles);
    }
}