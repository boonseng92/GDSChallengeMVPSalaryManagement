package com.gds.swe.challenge.validator;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination implements Pageable {
    private final int offset;//first record is 0
    private final int size;
    private final Sort sort;

    public Pagination(int offset, int size, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Page offset must not be less than zero!");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.offset = offset;
        this.size = size;
        this.sort = sort;
    }

    public int getPageNumber() {
        int page = offset / size;//TBD
        return page;
    }

    public Pageable next() {
        return new Pagination(offset + size, size, sort);
    }

    public Pageable previousOrFirst() {
        int prevoffset = offset - size;//TBD
        return new Pagination((prevoffset < 0 ? 0 : prevoffset), size, sort);
    }

    public Pageable first() {
        return new Pagination(0, size, sort);
    }

    public boolean hasPrevious() {
        return offset > 0;
    }

    public long getOffset() {
        return offset;
    }

    public int getPageSize() {
        return size;
    }

    public Sort getSort() {
        return sort;
    }
}
