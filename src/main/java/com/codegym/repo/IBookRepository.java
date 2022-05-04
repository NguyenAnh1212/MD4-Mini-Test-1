package com.codegym.repo;

import com.codegym.model.Book;
import com.codegym.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> findAllByCategory(Category category);
//    Iterable<Book> findAllByFirstNameContaining(String name);
}
