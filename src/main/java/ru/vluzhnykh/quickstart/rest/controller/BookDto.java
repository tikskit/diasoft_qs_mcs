package ru.vluzhnykh.quickstart.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private String author;
}
