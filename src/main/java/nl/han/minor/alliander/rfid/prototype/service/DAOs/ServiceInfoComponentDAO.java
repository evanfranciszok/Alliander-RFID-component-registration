package nl.han.minor.alliander.rfid.prototype.service.DAOs;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;

public class ServiceInfoComponentDAO {
  private ComponentDAO com;
  private String status;
  private boolean performAction;

  public ServiceInfoComponentDAO(ComponentDAO com, String status) {
    this.com = com;
    this.status = status;
    this.setPerformAction(false);
  }

  public boolean isPerformAction() {
    return performAction;
  }

  public void setPerformAction(boolean performAction) {
    this.performAction = performAction;
  }

  public ComponentDAO getCom() {
    return com;
  }

  public void setCom(ComponentDAO com) {
    this.com = com;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "infoCom [com=" + com.getrFID() + " (ser = " + com.getSerialNumber() + ")" + ", status=" + status
        + ", perform action=" + performAction + "]";
  }

}
