package nl.han.minor.alliander.rfid.prototype.persistence.interfaces;

import java.math.BigInteger;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;

public interface IComponentDatabase {
  public List<ComponentDAO> getAllComponents();

  public ComponentDAO getComponentFromRFID(String rFID);

  public ComponentDAO getComponentFromID(String id);

  public boolean addOrUpdateComponent(ComponentDAO com);
}
