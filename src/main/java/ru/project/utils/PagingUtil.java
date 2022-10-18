package ru.project.utils;

import java.util.ArrayList;
import java.util.List;

public class PagingUtil {

    public static List<Integer> findNumberOfPages(long itemsSize) {
        int numberOfPages = (int) Math.ceil((float) itemsSize / 10);
        List<Integer> pages = new ArrayList<>();
        for (int i = 0; i < numberOfPages; i++) {
            pages.add(i);
        }
        System.out.println(pages);
        return pages;
    }
}
