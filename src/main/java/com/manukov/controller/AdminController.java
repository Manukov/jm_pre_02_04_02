package com.manukov.controller;

import com.manukov.entity.User;
import com.manukov.service.RoleService;
import com.manukov.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        model.addAttribute("newUser", new User());
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

    @GetMapping()
    public String adminPage(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.getRoles());
        return "admin-page";     //admin Page
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
}
