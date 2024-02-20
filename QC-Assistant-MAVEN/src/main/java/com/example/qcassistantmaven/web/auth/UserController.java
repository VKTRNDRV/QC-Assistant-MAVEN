package com.example.qcassistantmaven.web.auth;

import com.example.qcassistantmaven.domain.dto.auth.UserDisplayDto;
import com.example.qcassistantmaven.domain.dto.auth.UserRoleEditDto;
import com.example.qcassistantmaven.service.auth.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<UserDisplayDto> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "users-all";
    }

    @GetMapping("/users/{id}")
    public String getEditUserRoles(@PathVariable Long id, Model model){
        UserDisplayDto user = this.userService.displayUser(id);
        model.addAttribute("user", user);
        return "users-edit";
    }

    @PostMapping("/users/edit")
    public String editUserRoles(UserRoleEditDto userRoleEditDto,
                                RedirectAttributes redirectAttributes){
        this.userService.editUserRoles(userRoleEditDto);

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @RequestMapping("/access-denied")
    public String accessDenied(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("accessDenied", true);
        return "redirect:/";
    }
}
