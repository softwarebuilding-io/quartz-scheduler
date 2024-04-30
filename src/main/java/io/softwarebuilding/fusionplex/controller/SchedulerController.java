package io.softwarebuilding.fusionplex.controller;

import io.softwarebuilding.fusionplex.dto.SchedulerJobInfoDto;
import io.softwarebuilding.fusionplex.enums.CronJob;
import io.softwarebuilding.fusionplex.enums.ScheduledJobs;
import io.softwarebuilding.fusionplex.service.SchedulerJobInfoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@SessionAttributes("schedulerJob")
public class SchedulerController {

    private final SchedulerJobInfoService schedulerJobInfoService;

    @Autowired
    public SchedulerController(final SchedulerJobInfoService schedulerJobInfoService) {
        this.schedulerJobInfoService = schedulerJobInfoService;
    }

    @GetMapping("/scheduler/manage")
    public String showManageSchedulerPage(final Model model) {
        model.addAttribute("schedulerJobs", this.schedulerJobInfoService.findAll());

        return "manageSchedulers";
    }

    @GetMapping("/scheduler/create")
    public String showCreateSchedulerJobPage(final Model model, final HttpSession session) {

        final SchedulerJobInfoDto dto = session.getAttribute("schedulerJob") != null
                ? (SchedulerJobInfoDto) session.getAttribute("schedulerJob") : new SchedulerJobInfoDto();

        this.prepareModelForSchedulerForm(model, dto, false);

        return "createEditSchedulerJobs";
    }

    @GetMapping("/scheduler/edit/{id}")
    public String showEditSchedulerJobPage(@PathVariable final UUID id, final Model model) {
        final SchedulerJobInfoDto dto = this.schedulerJobInfoService.findById(id);

        this.prepareModelForSchedulerForm(model, dto, true);

        return "createEditSchedulerJobs";
    }

    @PostMapping("/scheduler/save")
    public String saveSchedulerJob(@Valid @ModelAttribute("schedulerJob") final SchedulerJobInfoDto dto,
                                   final BindingResult result,
                                   final Model model,
                                   final RedirectAttributes redirectAttributes,
                                   final SessionStatus sessionStatus,
                                   final HttpSession session) {

        if (result.hasErrors()) {
            prepareModelForSchedulerForm(model, dto, false);
            session.setAttribute("errors", true);
            return "createEditSchedulerJobs";
        }

        this.schedulerJobInfoService.createOrUpdateJob(dto);

        sessionStatus.setComplete();
        session.removeAttribute("schedulerJob");
        session.removeAttribute("errors");

        redirectAttributes.addFlashAttribute("alertMessage", "Scheduler Job saved successfully");
        redirectAttributes.addFlashAttribute("alertType", "success");

        return "redirect:/scheduler/manage";
    }

    @PostMapping("/scheduler/edit/{jobId}")
    public String editSchedulerJob(@PathVariable final UUID jobId, final Model model) {

        final SchedulerJobInfoDto dto = this.schedulerJobInfoService.findById(jobId);

        this.prepareModelForSchedulerForm(model, dto, true);

        return "createEditSchedulerJobs";
    }

    @PostMapping("/scheduler/delete/{id}")
    public String deleteSchedulerJob(@PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
        this.schedulerJobInfoService.deleteJob(id);

        redirectAttributes.addFlashAttribute("alertMessage", "Scheduler Job deleted successfully");
        redirectAttributes.addFlashAttribute("alertType", "success");


        return "redirect:/scheduler/manage";
    }

    @PostMapping("/scheduler/unschedule/{id}")
    public String unscheduleSchedulerJob(@PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
        this.schedulerJobInfoService.unscheduleJob(id);

        redirectAttributes.addFlashAttribute("alertMessage", "Scheduler Job unscheduled successfully");
        redirectAttributes.addFlashAttribute("alertType", "success");


        return "redirect:/scheduler/manage";
    }

    @PostMapping("/scheduler/pause/{id}")
    public String pauseSchedulerJob(@PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
        this.schedulerJobInfoService.pauseJob(id);

        redirectAttributes.addFlashAttribute("alertMessage", "Scheduler Job paused successfully");
        redirectAttributes.addFlashAttribute("alertType", "success");


        return "redirect:/scheduler/manage";
    }

    @PostMapping("/scheduler/resume/{id}")
    public String resumeSchedulerJob(@PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
        this.schedulerJobInfoService.resumeJob(id);

        redirectAttributes.addFlashAttribute("alertMessage", "Scheduler Job resumed successfully");
        redirectAttributes.addFlashAttribute("alertType", "success");


        return "redirect:/scheduler/manage";
    }

    @PostMapping("/scheduler/shutdown")
    public String shutdownQuartzScheduler(final RedirectAttributes redirectAttributes) {
        this.schedulerJobInfoService.shutdownScheduler();

        redirectAttributes.addFlashAttribute("alertMessage", "Scheduler shutdown successfully");
        redirectAttributes.addFlashAttribute("alertType", "success");


        return "redirect:/scheduler/manage";
    }

    @PostMapping("/scheduler/start")
    public String startQuartzScheduler(final RedirectAttributes redirectAttributes) {
        this.schedulerJobInfoService.startScheduler();

        redirectAttributes.addFlashAttribute("alertMessage", "Scheduler initialized successfully");
        redirectAttributes.addFlashAttribute("alertType", "success");


        return "redirect:/scheduler/manage";
    }

    private void prepareModelForSchedulerForm(final Model model, final SchedulerJobInfoDto dto, final boolean editMode) {

        final CronJob selectedCronJob = dto.getCronJob() != null ? dto.getCronJob() : CronJob.NO;

        final ScheduledJobs selectedJobClass = dto.getJobClass() != null ? dto.getJobClass() : null;

        final String pageTitle = editMode ? "Edit Scheduler Job" : "Add New Scheduler Job";
        model.addAttribute("schedulerJob", dto);
        model.addAttribute("selectedCronJob", selectedCronJob);
        model.addAttribute("cronJobs", CronJob.values());
        model.addAttribute("jobClasses", ScheduledJobs.values());
        model.addAttribute("selectedJobClass", selectedJobClass);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("formAction", "/scheduler/save");
    }

}
