package nl.han.minor.alliander.rfid.prototype.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import nl.han.minor.alliander.rfid.prototype.service.interfaces.IInfoConnector;
import nl.han.minor.alliander.rfid.prototype.service.interfaces.IRFIDController;
import nl.han.minor.alliander.rfid.prototype.persistence.PFScanner;
import nl.han.minor.alliander.rfid.prototype.persistence.ScanMocker;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;

@Component
public class MainService implements IRFIDController {
  private static boolean scanStarted;
  private static IInfoConnector connector;
  private static IScanner scanner;
  private static List<ComponentDAO> tags = new ArrayList<ComponentDAO>();

  public MainService() {
    if (scanner == null) { // check if already initialized
      scanStarted = false;
      scanner = new PFScanner(); // ScanMocker();
      connector = new TagConnector(scanner);
    }
  }

  @Scheduled(fixedRate = 500)
  private void mainController() {
    if (scanStarted) {
      List<TagDAO> tags = scanner.scanTags();
      if (tags.size() > 0) {
        System.out.println("more than 0 tags!!");
        addUniqueComponentsToList(connector.getScannedComponents(tags));
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
  public List<ComponentDAO> getComponentsFromScan() {
    return tags;
  }

  @Override
  public void resetScan() {
    tags = new ArrayList<ComponentDAO>();
  }

  private void addUniqueComponentsToList(List<ComponentDAO> newTags) {
    System.out.println("new tags : " + newTags);
    if (newTags != null) {
      for (ComponentDAO newTag : newTags) {
        boolean isInList = false;
        for (ComponentDAO tag : tags) {
          if (tag.getId() == newTag.getId()) {
            isInList = true;
            break;
          }
        }
        if (!isInList) {
          System.out.println("adding " + newTag + " to list");
          tags.add(newTag);
        }
      }
    }
  }
}
