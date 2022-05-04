package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.BookForm;
import com.codegym.model.Category;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


@RestController
@PropertySource("classpath:upload_file.properties")
@RequestMapping("/ajax")
public class BookController {
    @Value("${file-upload}")
    private String fileUpload;
    @Autowired
    private IBookService bookService;
    @Autowired
    private ICategoryService categoryService;
    @ModelAttribute("categories")
    private Iterable<Category> listCategories(){
        return categoryService.findAll();
    }

    @GetMapping("/book")
    public ModelAndView listBook(){
        Iterable<Book> books = bookService.findAll();
        ModelAndView modelAndView = new ModelAndView("ajaxList");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> findAll(){
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id){
        Optional<Book> book = bookService.findById(id);
        System.out.println(book);
        if(!book.isPresent()){
            return new ResponseEntity<>(book.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id,@RequestBody Book book){
        book.setId(id);
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id){
        bookService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Hien thi danh sach
//    @GetMapping("/book")
//    public ModelAndView listBook(){
//        Iterable<Book> books = bookService.findAll();
//        ModelAndView modelAndView = new ModelAndView("book/list");
//        modelAndView.addObject("books", books);
//        return modelAndView;
//    }
//
//
//    //Tao moi sach
//    @GetMapping("/create")
//    public ModelAndView showCreateForm(){
//        ModelAndView modelAndView = new ModelAndView("book/create");
//        modelAndView.addObject("book", new BookForm());
//        return modelAndView;
//    }
//
//    @PostMapping("/save")
//    public ModelAndView createNewCustomer(@Validated @ModelAttribute("book") BookForm bookForm, BindingResult bindingResult){
//        MultipartFile multipartFile = bookForm.getImage();
//        String fileName = multipartFile.getOriginalFilename();
//        try{
//            FileCopyUtils.copy(bookForm.getImage().getBytes(),new File(fileUpload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Book book = new Book(bookForm.getName(),bookForm.getPrice(),bookForm.getAuthor(),fileName,bookForm.getCategory());
//        bookService.save(book);
//        ModelAndView modelAndView = new ModelAndView("book/create");
//        modelAndView.addObject("book",book);
//        modelAndView.addObject("message","created successfully!!!");
//        return modelAndView;
//    }
//
//    //Sua thong tin sach
//    @GetMapping("/edit/{id}")
//    public ModelAndView showEditForm(@PathVariable Long id){
//        Optional<Book> book = bookService.findById(id);
//        if (book.isPresent()){
//            ModelAndView modelAndView = new ModelAndView("book/edit");
//            modelAndView.addObject("book", book.get());
//            return modelAndView;
//        }else{
//            ModelAndView modelAndView = new ModelAndView("error.404");
//            return modelAndView;
//        }
//    }
//    @PostMapping("/update")
//    public ModelAndView update(@ModelAttribute("book")BookForm bookForm) {
////        bookService.save(book);
////        ModelAndView modelAndView = new ModelAndView("book/edit");
////        modelAndView.addObject("book", book);
////        modelAndView.addObject("message", "Edit successfully!");
////        return modelAndView;
//        MultipartFile multipartFile = bookForm.getImage();
//        String fileName = multipartFile.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(bookForm.getImage().getBytes(),new File(fileUpload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Book book = new Book(bookForm.getName(),bookForm.getPrice(),bookForm.getAuthor(),fileName,bookForm.getCategory());
//        bookService.remove(bookForm.getId());
//        bookService.save(book);
//        ModelAndView modelAndView = new ModelAndView("book/edit");
//        modelAndView.addObject("message","Updated Success");
//        modelAndView.addObject("book",book);
//        return modelAndView;
//    }
//    @GetMapping("/delete/{id}")
//    public ModelAndView showDeleteBookForm(@PathVariable Long id) {
//        Optional<Book> book = bookService.findById(id);
//        if (book.isPresent()) {
//            ModelAndView modelAndView = new ModelAndView("book/delete");
//            modelAndView.addObject("book", book.get());
//            return modelAndView;
//
//        } else {
//            ModelAndView modelAndView = new ModelAndView("error.404");
//            return modelAndView;
//        }
//    }
//
//    @PostMapping("/remove")
//    public String removeBook(@ModelAttribute("book") Book book) {
//        bookService.remove(book.getId());
//        return "redirect:book";
//    }

}
