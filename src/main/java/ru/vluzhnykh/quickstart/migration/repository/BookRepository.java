package ru.vluzhnykh.quickstart.migration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vluzhnykh.quickstart.migration.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
