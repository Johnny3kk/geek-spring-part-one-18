package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.entity.User;
import ru.geekbrains.persist.repo.UserRepository;
import ru.geekbrains.persist.repo.UserSpecification;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String allUsers(Model model, @RequestParam(value = "name", required = false) String name,
                                        @RequestParam("page")Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size) {
        logger.info("Filtering by name: {}", name);

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.Direction.ASC, "login");

        Specification<User> spec = UserSpecification.trueLiteral();

        if (name != null && !name.isEmpty()) {
            spec = spec.and(UserSpecification.loginLike(name));
        }

        model.addAttribute("usersPage", userRepository.findAll(spec, pageRequest));
        return "users";

    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
       User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
       model.addAttribute("user", user);
       return "user";
    }

    @PostMapping("/update")
    public String updateUser(User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user";
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundExceptionHandler(NotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.getModel().put("someAttr", "someValue");
        return modelAndView;
    }

}

