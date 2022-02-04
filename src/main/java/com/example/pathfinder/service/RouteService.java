package com.example.pathfinder.service;

import com.example.pathfinder.model.serviceModels.RouteServiceModel;
import com.example.pathfinder.model.viewModels.RouteDetailsViewModel;
import com.example.pathfinder.model.viewModels.RouteViewModel;

import java.util.List;

public interface RouteService {

    List<RouteViewModel> findAllRoutesView();

    void addNewRoute(RouteServiceModel routeServiceModel);

    RouteDetailsViewModel findById(Long id);
}
