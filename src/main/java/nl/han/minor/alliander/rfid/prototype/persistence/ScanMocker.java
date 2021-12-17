package nl.han.minor.alliander.rfid.prototype.persistence;

import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;

public class ScanMocker implements IScanner {

  @Override
  public List<TagDAO> scanTags() {
    List<TagDAO> RFIDs = new ArrayList<TagDAO>();
    RFIDs.add(new TagDAO("1054700164585905040406788177920000"));
    RFIDs.add(new TagDAO("973625604588042833743599039986227"));
    RFIDs.add(new TagDAO("973625604588042833742546286432096"));
    // RFIDs.add(new TagDAO("02313322"));
    // RFIDs.add(new TagDAO("0asda2"));
    // RFIDs.add(new TagDAO("300030155C97D461A7C0021D3D2A"));
    // RFIDs.add(new TagDAO("973625604588042833743672072258637"));
    // RFIDs.add(new TagDAO("973625604588042833742748698310134"));
    // RFIDs.add(new TagDAO("973625604588042834024356298916676"));
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
