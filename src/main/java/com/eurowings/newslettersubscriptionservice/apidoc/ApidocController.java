package com.eurowings.newslettersubscriptionservice.apidoc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/apidoc")
public class ApidocController {

    @RequestMapping(method = RequestMethod.GET)
    public View redirectFromDocumentationPathFromConvention() {
        return new RedirectView("/swagger-ui.html");
    }
}
