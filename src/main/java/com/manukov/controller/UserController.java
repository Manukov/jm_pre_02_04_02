package com.manukov.controller;

import com.manukov.entity.Role;
import com.manukov.entity.User;
import com.manukov.service.RoleService;
import com.manukov.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        model.addAttribute("newUser", new User("testLogin", "TestPass"));
        model.addAttribute("roles", roleService.getRoles());
        return "add-user-form";
    }

    @GetMapping("/showUpdateUserForm/{id}")
    public String showUpdateUserForm(@PathVariable(value = "id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("updateUser", user);
        model.addAttribute("roles", roleService.getRoles());
        return "update-user-form";
    }

    @PostMapping(value = "/addUser")
    public String addUser(@ModelAttribute("newUser") User newUser, @RequestParam(value = "selectedRoleId") String[] roles) {
        boolean result = userService.addUser(newUser, roles);
        return "redirect:/admin";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("updateUser") User user, @RequestParam(value = "selectedRoleId") String[] roles) {
        boolean result = userService.updateUser(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/")
    public String homePage() {
        return "home";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping(value = "/user")
    public String userPage(Authentication auth, Model model) {
        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        return "userpage";
    }

    @GetMapping(value = "/admin")
    public String adminPage(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.getRoles());
        return "adminpage";
    }
}
