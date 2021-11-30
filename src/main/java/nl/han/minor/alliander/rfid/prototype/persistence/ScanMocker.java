package nl.han.minor.alliander.rfid.prototype.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;

public class ScanMocker implements IScanner {

  @Override
  public List<BigInteger> scanTags() {
    List<BigInteger> RFIDs = new ArrayList<BigInteger>();
    RFIDs.add(new BigInteger("1054700164585905040406788177920000"));
    RFIDs.add(new BigInteger("973625604588042833743599039986227"));
    RFIDs.add(new BigInteger("973625604588042833743672072258637"));
    RFIDs.add(new BigInteger("973625604588042833742748698310134"));
    RFIDs.add(new BigInteger("973625604588042834024356298916676"));
    RFIDs.add(new BigInteger("973625604588042834025335804189546"));
    return RFIDs;
  }

  @Override
  public boolean startScan() {
    return true;
  }

  @Override
  public boolean stopScan() {
    return true;
  }

}