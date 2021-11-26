package com.tuannq.store.controller.appoiment;

import com.tuannq.store.entity.Work;
import com.tuannq.store.service.WorkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/works")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping("/all")
    public String showAllWorks(Model model) {
        model.addAttribute("works", workService.getAllWorks());
        return "works/list";
    }

    @GetMapping("/{workId}")
    public String showFormForUpdateWork(@PathVariable("workId") Long workId, Model model) {
        model.addAttribute("work", workService.getWorkById(workId));
        return "works/createOrUpdateWorkForm";
    }

    @GetMapping("/new")
    public String showFormForAddWork(Model model) {
        model.addAttribute("work", new Work());
        return "works/createOrUpdateWorkForm";
    }

    @PostMapping("/new")
    public String saveWork(@ModelAttribute("work") Work work) {
        if (work.getId() != null) {
            workService.updateWork(work);
        } else {
            workService.createNewWork(work);
        }
        return "redirect:/works/all";
    }

    @PostMapping("/delete")
    public String deleteWork(@RequestParam("workId") Long workId) {
        workService.deleteWorkById(workId);
        return "redirect:/works/all";
    }
}
