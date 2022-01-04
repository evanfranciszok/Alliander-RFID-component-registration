package nl.han.minor.alliander.rfid.prototype.service.interfaces;

import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;

public interface IInfoConnector {
  public List<ComponentDAO> getScannedComponents(List<TagDAO> ids);
}
