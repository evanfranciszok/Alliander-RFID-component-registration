package nl.han.minor.alliander.rfid.prototype.persistence;

import java.util.ArrayList;
import java.util.List;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;

public class ScanMocker implements IScanner {
  private int loopindex = 0;

  @Override
  public List<TagDAO> scanTags() {
    List<TagDAO> newList = new ArrayList<TagDAO>();
    newList.add(new TagDAO("973625604588042833743599039986227"));
    newList.add(new TagDAO("973625604588042833742546286432096"));
    newList.add(new TagDAO("02313322"));
    newList.add(new TagDAO("1054700164585905040406788177920000"));
    // newList.add(new TagDAO("0asda2"));
    // newList.add(new TagDAO("300030155C97D461A7C0021D3D2A"));
    // newList.add(new TagDAO("973625604588042833743672072258637"));
    // newList.add(new TagDAO("973625604588042833742748698310134"));
    // newList.add(new TagDAO("973625604588042834024356298916676"));
    List<TagDAO> RFIDs = new ArrayList<TagDAO>();
    RFIDs.add(newList.get(loopindex / 3));
    loopindex++;
    if (loopindex == newList.size() * 3)
      loopindex = 0;
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
