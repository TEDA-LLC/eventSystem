package com.example.eventsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomPage<T> {
    List<T> content;
    boolean empty, first, last;

    int number;
    int numberOfElements;
    long totalElements;
    int totalPages;
    int size;

    public CustomPage(Page<?> page, List<T> content) {
        this.empty = page.isEmpty();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
        if (content == null) {
            this.content = List.of();
        }
        else {
            this.content = content;
        }
    }
}
