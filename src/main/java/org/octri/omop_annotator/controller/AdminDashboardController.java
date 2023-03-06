package org.octri.omop_annotator.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @GetMapping("")
    public String show(Map<String, Object> model) {
        model.put("pageScripts", new String[] { "vendor.js", "admin-dashboard.js" });
        return "admin/dashboard/show";
    }

}
