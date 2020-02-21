package com.kristyn.kristynlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listJobs(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String jobForm(Model model) {
        model.addAttribute("job", new Job());
        return "jobform";
    }

    @PostMapping("/add")
    public String processJob(@Valid @ModelAttribute Job job, BindingResult result,
                             @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "jobform";
        }
        if (file.isEmpty()) {
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            job.setPicture(uploadResult.get("url").toString());
            jobRepository.save(job);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return "redirect:/";
    }

 /*   @PostMapping("/process")
    public String processForm(@Valid Job job, BindingResult result){
        if(result.hasErrors()){
            return "jobform";
        }
        jobRepository.save(job);
        return "redirect:/";*/

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model) {
        model.addAttribute("job", jobRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model) {
        model.addAttribute("job", jobRepository.findById(id).get());
        return "jobform";
    }

    @RequestMapping("delete/{id}")
    public String deleteJob(@PathVariable("id") long id) {
        jobRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }
}
