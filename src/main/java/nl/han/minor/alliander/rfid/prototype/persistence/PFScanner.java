package nl.han.minor.alliander.rfid.prototype.persistence;

import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;

public class PFScanner implements IScanner {

  @Override
  public long[] scanTags() {
    return new long[] { 54321, 12345, 13579 };
  }

}
