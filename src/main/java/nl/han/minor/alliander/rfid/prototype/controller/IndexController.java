package nl.han.minor.alliander.rfid.prototype.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import nl.han.minor.alliander.rfid.prototype.service.TagConnector;
import nl.han.minor.alliander.rfid.prototype.service.interfaces.IInfoConnector;

@Controller
public class IndexController {
  @Value("${spring.application.name}")
  String appName;

  @GetMapping("/")
  public String homePage(Model model) {
    return "home";
  }

  @GetMapping("/gettags")
  public String gettags(Model model) {
    IInfoConnector tags = new TagConnector();
    model.addAttribute("tags", tags.getScannedTags());
    return "tags";
  }
}
