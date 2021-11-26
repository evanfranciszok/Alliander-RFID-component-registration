package nl.han.minor.alliander.rfid.prototype.service;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import nl.han.minor.alliander.rfid.prototype.service.interfaces.IInfoConnector;
import nl.han.minor.alliander.rfid.prototype.service.interfaces.IRFIDController;
import nl.han.minor.alliander.rfid.prototype.persistence.PFScanner;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;

@Component
public class MainService implements IRFIDController {
  private static boolean scanStarted;
  private static IInfoConnector connector;
  private static IScanner scanner;
  private static List<TagDAO> tags = new ArrayList<TagDAO>();

  public MainService() {
    if (scanner == null) { // check if already initialized
      scanStarted = false;
      scanner = new PFScanner();
      connector = new TagConnector(scanner);
    }
  }

  @Scheduled(fixedRate = 500)
  private void mainController() {
    if (scanStarted) {
      List<BigInteger> ids = scanner.scanTags();
      if (ids.size() > 0) {
        addUniqueTagsToList(connector.getScannedTags(ids));
      }
    }
  }

  @Override
  public void startScan() {
    if (!scanStarted) {
      if (scanner.startScan()) {// check if succesfully started
        scanStarted = true;
      }
    }
  }

  @Override
  public void stopScan() {
    if (scanStarted) {
      if (scanner.stopScan()) {// check if succesfully stopped
        scanStarted = false;
      }
    }
  }

  @Override
  public List<TagDAO> getTagsFromScan() {
    return tags;
  }

  private void addUniqueTagsToList(List<TagDAO> newTags) {
    if (newTags != null) {
      for (TagDAO newTag : newTags) {
        boolean isInList = false;
        for (TagDAO tag : tags) {
          if (tag.getId().equals(newTag.getId())) {
            isInList = true;
            break;
          }
        }
        if (!isInList) {
          tags.add(newTag);
        }
      }
    }
  }
}
