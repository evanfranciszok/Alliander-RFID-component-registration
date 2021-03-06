package nl.han.minor.alliander.rfid.prototype.persistence.interfaces;

import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.MSRDAO;

public interface IComponentDatabase {
  public ComponentDAO getComponentFromRFID(String rFID);

  public ComponentDAO getComponentFromID(String id);

  public boolean addOrUpdateComponent(ComponentDAO com);

  public List<MSRDAO> getAllMSRs();

  /**
   * Get all the components from the database
   * 
   * @return List<ComponentDAO>
   */
  public List<ComponentDAO> getAllComponents();

  public List<ComponentDAO> getAllComponentFromMSR(int mSRId);

  public boolean addComToMSR(ComponentDAO com, Integer selectedMSRId);

  public boolean removeComToMSR(ComponentDAO com, Integer selectedMSRId);
}
