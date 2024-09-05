package com.senla.util.repository.casual;

import com.senla.util.repository.Identifiable;

public abstract class LongIdRepository<T extends Identifiable<Long>> extends AbstractRepository<T, Long> {
    @Override
    protected Long idGenerateNext() {
        return ++currentId;
    }
    @Override
    protected void degradeCurrentId(){
        --currentId;
    };
}