package nl.han.minor.alliander.rfid.prototype.persistence.DAOs;

import java.io.Serializable;

public class TagDAO implements Serializable {

  public TagDAO(long id, String supplier, String name, String dOI) {
    this.id = id;
    this.supplier = supplier;
    this.name = name;
    this.dateOfInstallment = dOI;
  }

  private long id;
  private String supplier;
  private String name;
  private String dateOfInstallment;

  @Override
  public String toString() {
    return id + supplier;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDateOfInstallment() {
    return dateOfInstallment;
  }

  public void setDateOfInstallment(String dateOfInstallment) {
    this.dateOfInstallment = dateOfInstallment;
  }
}
