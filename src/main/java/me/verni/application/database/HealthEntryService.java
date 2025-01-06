package me.verni.application.database;

import java.util.List;

public class HealthEntryService {

    private final HealthEntryDao healthEntryDao;

    public HealthEntryService() {
        this.healthEntryDao = new HealthEntryDao();
    }

    public void addHealthEntry(HealthEntry entry) {
        if (!isValidEntry(entry)) {
            throw new IllegalArgumentException("Nieprawidłowe dane wpisu.");
        }
        healthEntryDao.saveEntry(entry);
    }

    public HealthEntry getHealthEntryById(int id) {
        return healthEntryDao.getEntryById(id);
    }

    public void updateHealthEntry(HealthEntry entry){
        if (!isValidEntry(entry)) {
            throw new IllegalArgumentException("Nieprawidłowe dane wpisu.");
        }
        healthEntryDao.updateEntry(entry);
    }

    public void deleteHealthEntry(int id) {
        healthEntryDao.deleteEntry(id);
    }

    public List<HealthEntry> getAllHealthEntries() {
        return healthEntryDao.getAllEntries();
    }

    private boolean isValidEntry(HealthEntry entry) {
        if (entry.getDate() == null) return false;
        if (entry.getTime() == null) return false;
        if (entry.getWeight() <= 0) return false;
        if (entry.getSystolicPressure() <=0 || entry.getSystolicPressure() > 300) return false;
        if (entry.getDiastolicPressure() <= 0 || entry.getDiastolicPressure() > 200) return false;
        return entry.getMood() != null && !entry.getMood().trim().isEmpty();
    }
}