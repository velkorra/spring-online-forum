package org.example.onlineforum.dto;

import java.util.List;

public class PagedList<T extends List<T>> {
    private List<T> items;
    private int totalPages;
    private int a;
}
