package com.senla.util.repository;

import java.util.UUID;

public abstract class UUIDRepository<T extends Identifiable<UUID>> extends AbstractRepository<T, UUID> {
    @Override
    protected UUID idGenerateNext() {
        return UUID.randomUUID();
    }
    @Override
    protected void degradeCurrentId(){};
}
