package com.senla.util.repository.casual;

import com.senla.util.repository.Identifiable;

import java.util.UUID;

public abstract class UUIDRepository<T extends Identifiable<UUID>> extends AbstractRepository<T, UUID> {
    @Override
    protected UUID idGenerateNext() {
        return UUID.randomUUID();
    }
    @Override
    protected void degradeCurrentId(){};
}
