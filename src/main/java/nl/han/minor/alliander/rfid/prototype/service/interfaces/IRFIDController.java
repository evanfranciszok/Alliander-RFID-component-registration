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

  public void resetScan();

  public ComponentDAO getComponent(String id);

  public boolean addOrUpdateComponent(ComponentDAO com);
}