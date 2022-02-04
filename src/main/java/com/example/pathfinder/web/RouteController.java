package com.example.pathfinder.web;

import com.example.pathfinder.model.bindingModels.RouteAddBindingModel;
import com.example.pathfinder.model.serviceModels.RouteServiceModel;
import com.example.pathfinder.model.viewModels.RouteViewModel;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;

    public RouteController(RouteService routeService, CurrentUser currentUser, ModelMapper modelMapper) {
        this.routeService = routeService;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id,Model model){
        model.addAttribute("route",routeService.findById(id));

        return "route-details";
    }


    @GetMapping("/all")
    public String allRoutes(Model model){
       List<RouteViewModel> routeViewModelList = routeService.findAllRoutesView();
       model.addAttribute("routes",routeViewModelList);
        return "routes";
    }

    @GetMapping("/add")
    public String add(){
        return "add-route";
    }

    @PostMapping("/add")
    public String addRoute(@Valid RouteAddBindingModel routeAddBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws IOException {

        if(bindingResult.hasErrors()){

            redirectAttributes
                    .addFlashAttribute("routeAddBindingModel",routeAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.routeAddBindingModel",
                            bindingResult);

            return "redirect:add";

        }

        RouteServiceModel routeServiceModel =
                modelMapper.map(routeAddBindingModel,RouteServiceModel.class);
        String gpx = new String(routeAddBindingModel
                .getGpxCoordinates().getBytes());
//        File file = new File()
//        file.

        routeServiceModel.setGpxCoordinates(new String(routeAddBindingModel
                .getGpxCoordinates().getBytes()));

        routeService.addNewRoute(routeServiceModel);

        return "redirect:all";
    }

    @ModelAttribute
    public RouteAddBindingModel routeAddBindingModel(){
        return new RouteAddBindingModel();
    }





}
