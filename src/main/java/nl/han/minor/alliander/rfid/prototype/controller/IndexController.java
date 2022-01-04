package nl.han.minor.alliander.rfid.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.ComponentDAO;
import nl.han.minor.alliander.rfid.prototype.service.MainService;
import nl.han.minor.alliander.rfid.prototype.service.DAOs.ServiceInfoComponentDAO;
import nl.han.minor.alliander.rfid.prototype.service.interfaces.IRFIDController;

@Controller
public class IndexController {
  @Value("${spring.application.name}")
  String appName;
  IRFIDController rfid = new MainService();

  @GetMapping("/")
  public String homePage(Model model) {
    return "home";
  }

  @GetMapping("/api/startscan")
  public ResponseEntity<String> startScan() {
    rfid.startScan();
    return new ResponseEntity<>("scan started", HttpStatus.OK);
  }

  @GetMapping("/api/stopscan")
  public ResponseEntity<String> stopScan() {
    rfid.stopScan();
    return new ResponseEntity<>("scan started", HttpStatus.OK);
  }

  @GetMapping("/api/resetscan")
  public ResponseEntity<String> resetScan() {
    rfid.resetScan();
    return new ResponseEntity<>("scan started", HttpStatus.OK);
  }

  // get All MSRs
  @GetMapping("/api/allMSR")
  public String getAllMSR(Model model) {
    model.addAttribute("msrs", rfid.getAllMSRs());
    return "msrs";
  }

  // get all com for msr
  @GetMapping("/api/allCom")
  public String getAllCom(Model model) {
    List<ComponentDAO> allComponents = rfid.getAllComponents();
    // System.out.println(allComponents);
    model.addAttribute("components", allComponents);
    model.addAttribute("name", "Amount of components");
    model.addAttribute("amount", allComponents.size());
    return "components";
  }

  // get all components for
  @GetMapping("/{MSRid}")
  public String getMSRInfo(Model model, @PathVariable("MSRid") int mSRid) {
    model.addAttribute("comInMsr", true);
    return "home";
  }

  // get all components
  @GetMapping("/api/allCom/{MSRid}")
  public String getAllComForMSR(Model model, @PathVariable("MSRid") int mSRid) {
    model.addAttribute("components", rfid.getAllComponents(mSRid));
    model.addAttribute("name", "Id of MSR");
    model.addAttribute("amount", mSRid);
    model.addAttribute("showButtons", true);
    return "components";
  }

  // get all components
  @GetMapping("/api/allComDiff/{MSRid}")
  public String getAllComForMSRWithDifference(Model model, @PathVariable("MSRid") int mSRid) {
    model.addAttribute("components", rfid.getInfoOfScanForMSR(mSRid));
    model.addAttribute("name", "Id of MSR");
    model.addAttribute("amount", mSRid);
    model.addAttribute("showButtons", true);
    return "componentdiff";
  }

  // get all tags
  @GetMapping("/api/allTags")
  public String getAllTags(Model model) {
    model.addAttribute("components", rfid.getComponentsFromScan());
    model.addAttribute("name", "Amount of Tags");
    model.addAttribute("amount", rfid.getComponentsFromScan().size());
    model.addAttribute("showButtons", true);
    model.addAttribute("showResetButton", true);
    return "components";
  }

  @GetMapping("/list")
  public String makelist(Model model) {
    return "list";
  }

  @GetMapping("/api/gettags")
  public String getTags(Model model) {
    model.addAttribute("tags", rfid.getComponentsFromScan());
    return "tags2";
  }

  @GetMapping("/api/undoDifference")
  public ResponseEntity<String> undoDifference(Model model) {
    System.out.println("UndoDifference");
    if (rfid.getCurrentInfoComponentForMSR() != null)
      rfid.getCurrentInfoComponentForMSR().setPerformAction(false);
    return new ResponseEntity<>("scan started", HttpStatus.OK);
  }

  @GetMapping("/api/performDifference")
  public ResponseEntity<String> performDifference(Model model) {
    System.out.println("performDifference");
    if (rfid.getCurrentInfoComponentForMSR() != null)
      rfid.getCurrentInfoComponentForMSR().setPerformAction(true);
    return new ResponseEntity<>("scan started", HttpStatus.OK);
  }

  @GetMapping("/api/getNextCom")
  public ResponseEntity<String> getNextComponent(Model model) {
    rfid.getNextInfoComponent();
    return new ResponseEntity<>("scan started", HttpStatus.OK);
  }

  @GetMapping("/api/getDifference")
  public String getDifference(Model model) {
    ServiceInfoComponentDAO diff = rfid.getCurrentInfoComponentForMSR();
    if (diff != null) {
      model.addAttribute("difference", diff);
      return "difference";
    }
    rfid.UpdateMSR();
    rfid.resetScan();
    return "noDifference";
  }

  @GetMapping("/edit/{rfid}")
  public String getEditComponent(Model model, @PathVariable("rfid") String rfid) {
    model.addAttribute("component", createNewOrFindComponent(rfid));
    return "editComponent";
  }

  @PostMapping("/edit/{rfid}")
  public String editComponent(@ModelAttribute ComponentDAO com, Model model, @PathVariable("rfid") String rfid) {
    com.setrFID(rfid);
    System.out.println("the rfid code" + rfid);
    if (this.rfid.addOrUpdateComponent(com)) {
      model.addAttribute("succes", true);
    }
    model.addAttribute("component", createNewOrFindComponent(rfid));
    return "editComponent";
  }

  @GetMapping("/edit/msr/{msrid}")
  public String editMsrComponents(Model model, @PathVariable("msrid") int mSRId) {
    return "editComponentsInMSR";
  }

  private ComponentDAO createNewOrFindComponent(String rfid) {
    ComponentDAO com = this.rfid.getComponent(rfid);
    if (com == null) {
      com = new ComponentDAO(0, rfid, null, null, null, null, null, null, null);
    }
    return com;
  }
}
