package nl.han.minor.alliander.rfid.prototype.service.interfaces;

import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.MSRDAO;

/**
 * Itest
 */
public interface IRFIDController {
  public void startScan();

  public void stopScan();

  public List<ComponentDAO> getComponentsFromScan();

  public List<MSRDAO> getAllMSRs();

  public void resetScan();

  public ComponentDAO getComponent(String id);

  public boolean addOrUpdateComponent(ComponentDAO com);

  public List<ComponentDAO> getAllComponents();

  public List<ComponentDAO> getAllComponents(int mSRid);
}