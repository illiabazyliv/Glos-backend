package com.glos.feedservice.domain.DTO;

import java.util.List;

public class PageDTO<T>
{
    private List<T> content;
    private Integer page;
    private Integer size;
    private String sort;
    private Integer totalSize;

    public PageDTO(List<T> content, Integer page, Integer size, String sort, Integer totalSize) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.totalSize = totalSize;
    }

    public List<T> getContent() {
        return content;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public String getSort() {
        return sort;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public PageDTO() {
    }
}
