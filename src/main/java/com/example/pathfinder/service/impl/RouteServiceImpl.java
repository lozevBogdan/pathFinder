package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.serviceModels.RouteServiceModel;
import com.example.pathfinder.model.viewModels.RouteDetailsViewModel;
import com.example.pathfinder.model.viewModels.RouteViewModel;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.service.CategoryService;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final UserService userService;

    public RouteServiceImpl(RouteRepository routeRepository, ModelMapper modelMapper,
                            CategoryService categoryService, UserService userService) {
        this.routeRepository = routeRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @Override
    public List<RouteViewModel> findAllRoutesView() {
        return routeRepository
                .findAll()
                .stream()
                .map(r->{
                    RouteViewModel routeViewModel = modelMapper.map(r,RouteViewModel.class);

                    if(r.getPictures().isEmpty()){
                        routeViewModel.setPictureUrl("/images/pic4.jpg");
                    }else {
                        routeViewModel.setPictureUrl(r.getPictures().stream().findAny().get().getUrl());
                    }

                    //routeViewModel.setPictureUrl(r.getPictures().isEmpty() ? "/images/pic4.jpg" : r.getPictures().)

                    return routeViewModel;
                }).collect(Collectors.toList());
    }

    @Override
    public void addNewRoute(RouteServiceModel routeServiceModel) {

        Route route = modelMapper.map(routeServiceModel,Route.class);
        route.setAuthor(userService.findCurrentLoginUserEntity());

        route.setCategories(routeServiceModel
                .getCategories()
                .stream()
                .map(categoryNameEnum-> categoryService.findCategoryByName(categoryNameEnum))
                .collect(Collectors.toSet()));


        routeRepository.save(route);

    }

    @Override
    public RouteDetailsViewModel findById(Long id) {
        return routeRepository.findById(id)
                .map(r->modelMapper.map(r, RouteDetailsViewModel.class))
                .orElse(null);
    }
}
