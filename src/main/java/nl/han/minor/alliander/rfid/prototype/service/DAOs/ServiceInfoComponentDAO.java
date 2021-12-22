package nl.han.minor.alliander.rfid.prototype.service.DAOs;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;

public class ServiceInfoComponentDAO {
  private ComponentDAO com;
  private String status;

  public ServiceInfoComponentDAO(ComponentDAO com, String status) {
    this.com = com;
    this.status = status;
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
    return "infoCom [com=" + com.getrFID() + ", status=" + status + "]";
  }

}
