package ru.vluzhnykh.quickstart.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vluzhnykh.quickstart.transactions.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
