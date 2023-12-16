package com.stav.server.dto;

import java.util.List;

public class CategoryData {
    private List<CategoryDTO> categories;
    private int page;
    private int totalPages;
    private int totalCategories;

    public CategoryData(List<CategoryDTO> categories, int page, int totalPages, int totalCategories) {
        this.categories = categories;
        this.page = page;
        this.totalPages = totalPages;
        this.totalCategories = totalCategories;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(int totalCategories) {
        this.totalCategories = totalCategories;
    }

    @Override
    public String toString() {
        return "CategoryData{" +
                "categories=" + categories +
                ", page=" + page +
                ", totalPages=" + totalPages +
                ", totalCategories=" + totalCategories +
                '}';
    }
}
