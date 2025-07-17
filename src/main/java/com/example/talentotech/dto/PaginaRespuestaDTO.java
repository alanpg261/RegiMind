package com.example.talentotech.dto;

import java.util.List;

public class PaginaRespuestaDTO<T> {
    
    private List<T> content;
    private Integer number;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
    private Boolean first;
    private Boolean last;
    private Boolean hasNext;
    private Boolean hasPrevious;

    // Constructor por defecto
    public PaginaRespuestaDTO() {}

    // Constructor con parámetros
    public PaginaRespuestaDTO(List<T> content, Integer number, Integer size, 
                             Long totalElements) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.first = number == 0;
        this.last = number >= totalPages - 1;
        this.hasNext = !last;
        this.hasPrevious = !first;
    }

    // Getters y setters
    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public Long getTotalElements() { return totalElements; }
    public void setTotalElements(Long totalElements) { this.totalElements = totalElements; }

    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }

    public Boolean getFirst() { return first; }
    public void setFirst(Boolean first) { this.first = first; }

    public Boolean getLast() { return last; }
    public void setLast(Boolean last) { this.last = last; }

    public Boolean getHasNext() { return hasNext; }
    public void setHasNext(Boolean hasNext) { this.hasNext = hasNext; }

    public Boolean getHasPrevious() { return hasPrevious; }
    public void setHasPrevious(Boolean hasPrevious) { this.hasPrevious = hasPrevious; }
} 