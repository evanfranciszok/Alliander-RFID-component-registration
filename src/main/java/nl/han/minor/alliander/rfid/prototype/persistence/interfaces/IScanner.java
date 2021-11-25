package nl.han.minor.alliander.rfid.prototype.persistence.interfaces;

import java.math.BigInteger;
import java.util.List;

public interface IScanner {
  public List<BigInteger> scanTags();
}
