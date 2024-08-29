package com.senla.repositories;

public interface Identifiable<ID> {
    ID getId();
    void setId(ID id);
}
