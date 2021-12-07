package nl.han.minor.alliander.rfid.prototype.service.interfaces;

import java.math.BigInteger;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;

public interface IInfoConnector {
  public List<ComponentDAO> getScannedComponents(List<BigInteger> ids);
}
