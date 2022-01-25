package ru.vluzhnykh.quickstart.mcstesting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vluzhnykh.quickstart.mcstesting.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
