package nl.han.minor.alliander.rfid.prototype.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import nl.han.minor.alliander.rfid.prototype.persistence.TagRepository;

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
    TagRepository tags = new TagRepository();
    model.addAttribute("tags", tags.getTags());
    return "tags";
  }

  @GetMapping("/list")
  public String makelist(Model model) {
    return "list";
  }
}
