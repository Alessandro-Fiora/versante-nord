package org.lessons.java.versante_nord.controller;

import java.util.List;

import org.lessons.java.versante_nord.model.Book;
import org.lessons.java.versante_nord.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @GetMapping
    public String index(Model model) {

        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        
        return "books/show";
    }

    @GetMapping("/create")
    public String create(Model model){
        Book book = new Book();
        book.setImmagine("https://fakeimg.pl/456x638/ff6666/ffffff?text=Book%20Cover");
        model.addAttribute("book", book);
        return "books/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model ) {
        
        if(bindingResult.hasErrors()){
            return "books/create-or-edit";
        }

        bookService.create(formBook);

        return "redirect:/books/" + formBook.getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("edit", true);
        model.addAttribute("book", bookService.getById(id));

        return "books/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model ) {
        if(bindingResult.hasErrors()){
            return "books/create-or-edit";
        }
        bookService.update(formBook);
        
        return "redirect:/books/" + formBook.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        
        bookService.deleteById(id);

        return "redirect:/books";
    }


}
