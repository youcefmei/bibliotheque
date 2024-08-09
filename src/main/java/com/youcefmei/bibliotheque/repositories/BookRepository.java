package com.youcefmei.bibliotheque.repositories;

import com.youcefmei.bibliotheque.models.Book;
import java.util.List;



public interface BookRepository {
    List<Book> findAll();

}
