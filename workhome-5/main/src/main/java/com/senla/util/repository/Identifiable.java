package com.senla.util.repository;

public interface Identifiable<ID> {
    ID getId();
    void setId(ID id);
}
