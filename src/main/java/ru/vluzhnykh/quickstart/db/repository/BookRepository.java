package ru.vluzhnykh.quickstart.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vluzhnykh.quickstart.db.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
