package nl.han.minor.alliander.rfid.prototype.persistence.interfaces;

import java.math.BigInteger;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.MSRDAO;

public interface IComponentDatabase {
  public ComponentDAO getComponentFromRFID(String rFID);

  public ComponentDAO getComponentFromID(String id);

  public boolean addOrUpdateComponent(ComponentDAO com);

  public List<MSRDAO> getAllMSRs();

  public List<ComponentDAO> getAllComponents();
}
