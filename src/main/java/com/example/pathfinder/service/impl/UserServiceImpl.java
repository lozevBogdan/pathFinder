package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.serviceModels.UserServiceModel;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {

        User user = modelMapper.map(userServiceModel,User.class);

        user.setLevel(LevelEnum.BEGINNER);

        userRepository.save(user);


    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password) {

        return userRepository.
                findByUsernameAndPassword(username,password)
                .map(u->modelMapper.map(u,UserServiceModel.class))
                .orElse(null);
    }
    @Override
    public void loginUser(Long id, String username) {
        currentUser.setUsername(username);
        currentUser.setId(id);
    }

    @Override
    public void logout() {
        currentUser.setUsername(null);
        currentUser.setId(null);
    }
}
