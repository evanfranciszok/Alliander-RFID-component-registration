package nl.han.minor.alliander.rfid.prototype.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

  @GetMapping("/api/getcomponentsfromscan")
  public String getComponentsFromScan(Model model) {
    model.addAttribute("components", rfid.getComponentsFromScan());
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
}
