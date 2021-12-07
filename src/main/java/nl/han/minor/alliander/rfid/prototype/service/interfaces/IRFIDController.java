package nl.han.minor.alliander.rfid.prototype.service.interfaces;

import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;

/**
 * Itest
 */
public interface IRFIDController {
  public void startScan();

  public void stopScan();

  public List<ComponentDAO> getComponentsFromScan();
  // ophalen verschil
  // setten van msr ID
  // ophalen van msr ID's
  // ophalen bekende gegevens van MSR

  public void resetScan();
}