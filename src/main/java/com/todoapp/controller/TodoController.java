package com.todoapp.controller;

import com.todoapp.model.Todo;
import com.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todos", repository.findAll());
        model.addAttribute("todo", new Todo()); // nuevo por defecto
        return "todo";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("todos", repository.findAll());
        model.addAttribute("todo", repository.findById(id).orElse(new Todo())); // edita si existe
        return "todo";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Todo todo) {
        repository.save(todo);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/toggle")
    public String toggleCompleted(@RequestParam Long id) {
        repository.findById(id).ifPresent(todo -> {
            todo.setCompleted(!todo.isCompleted());
            repository.save(todo);
        });
        return "redirect:/";
    }

}
