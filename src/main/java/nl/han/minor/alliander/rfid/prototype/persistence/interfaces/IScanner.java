package nl.han.minor.alliander.rfid.prototype.persistence.interfaces;

import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;

public interface IScanner {
  public List<TagDAO> scanTags();

  public boolean startScan();

  public boolean stopScan();
}
