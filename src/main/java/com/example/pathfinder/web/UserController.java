package com.example.pathfinder.web;

import com.example.pathfinder.model.bindingModels.UserLoginBindingModel;
import com.example.pathfinder.model.bindingModels.UserRegisterBindingModel;
import com.example.pathfinder.model.serviceModels.UserServiceModel;
import com.example.pathfinder.model.viewModels.UserViewModel;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    //Another way is present in rows 29 and 30 !!!!
    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }

    @GetMapping("/register")
    public String register(Model model){

//          Another way is present in rows 21 and 22 !!!!
//        if(!model.containsAttribute("userRegisterBindingModel")){
//            model.addAttribute("userRegisterBindingModel",new UserRegisterBindingModel());
//        }

        return "register";
    }

    @PostMapping("/register")
        public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel,
          BindingResult bindingResult, RedirectAttributes redirectAttributes){

//        System.err.println(userRegisterBindingModel.getUsername());
//        System.err.println(userRegisterBindingModel.getAge());
//        System.err.println(userRegisterBindingModel.getFullname());
//        System.err.println(userRegisterBindingModel.getEmail());
//        System.err.println(userRegisterBindingModel.getPassword());

            if(bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().
                    equals(userRegisterBindingModel.getPasswordConfirm())){
                redirectAttributes.
                        addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel);
                redirectAttributes.
                        addFlashAttribute(
                                "org.springframework.validation.BindingResult.userRegisterBindingModel",
                                bindingResult);
              //  System.err.println(bindingResult.toString());
                return "redirect:register";
            }

            boolean isNameExist =
                    userService.isNameExist(userRegisterBindingModel.getUsername());

            if(isNameExist){
                //TODO redirect with message
            }

            userService.registerUser(modelMapper.
                    map(userRegisterBindingModel, UserServiceModel.class));


        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("isExist",true);
        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult,RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            redirectAttributes
                    .addFlashAttribute("userLoginBindingModel",userLoginBindingModel)
                    .addFlashAttribute(
                    "org.Springframework.validation.BindingResult.userLoginBindingModel",
                    bindingResult);

            return "redirect:login";

        }

        UserServiceModel user = userService.findByUsernameAndPassword(
                userLoginBindingModel.getUsername(),userLoginBindingModel.getPassword());

        if(user == null){

            redirectAttributes
                    .addFlashAttribute("isExist",false)
                    .addFlashAttribute("userLoginBindingModel",userLoginBindingModel)
                    .addFlashAttribute(
                            "org.Springframework.validation.BindingResult.userLoginBindingModel",
                            bindingResult);
            return "redirect:login";
        }

        userService.loginUser(user.getId(),user.getUsername());

        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(){
       userService.logout();
        return "redirect:/";
    }

    @GetMapping("/profile/{id}")
    private String profile(@PathVariable Long id,Model model){

        model.addAttribute("user",
                modelMapper.
                        map(userService.findById(id),UserViewModel.class));

        return "profile";

    }




}
