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
  public String getAllComForMSR(Model model, @PathVariable("MSRid") String mSRid) {
    model.addAttribute("components", rfid.getAllComponents(Integer.valueOf(mSRid)));
    model.addAttribute("name", "Id of MSR");
    model.addAttribute("amount", mSRid);
    return "components";
  }

  // get all tags
  @GetMapping("/api/allTags")
  public String getAllTags(Model model) {
    model.addAttribute("components", rfid.getComponentsFromScan());
    model.addAttribute("name", "Amount of Tags");
    model.addAttribute("amount", rfid.getComponentsFromScan().size());
    model.addAttribute("showButtons", true);
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

  private ComponentDAO createNewOrFindComponent(String rfid) {
    ComponentDAO com = this.rfid.getComponent(rfid);
    if (com == null) {
      com = new ComponentDAO(0, rfid, null, null, null, null, null, null, null);
    }
    return com;
  }
}
