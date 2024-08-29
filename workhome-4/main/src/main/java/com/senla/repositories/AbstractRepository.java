package com.senla.repositories;

import java.io.Serializable;
import java.util.*;

public abstract class AbstractRepository<T extends Identifiable<ID>, ID extends Serializable> implements Repository<T, ID>{

    protected Map<ID, T> idMap = new HashMap<>();
    protected long counter = 0L;
    protected long currentId = 0L;

    @Override
    public long count() {
        return counter;
    }

    @Override
    public T save(T entity) {
        ID idToSave = entity.getId();
        if(idToSave == null) {
            idToSave = idGenNext();

            postSaveProcessEntity(entity);
            entity.setId(idToSave);

            idMap.put(idToSave, entity);
            counter++;
        } else {
            if(!idMap.containsKey(idToSave)) throw new RuntimeException("Absent Id");

            postUpdateProcessEntity(entity);
            idMap.replace(idToSave, entity);
        }
        return idMap.get(idToSave);
    }

    @Override
    public void deleteById(ID id) {
        T entity = idMap.get(id);
        if(entity != null) {
            idMap.remove(id);
            counter--;
        }
    }
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(idMap.get(id));
    }
    @Override
    public Collection<T> findAll() {
        return idMap.values();
    }

    @Override
    public boolean existsById(ID id) {
        return idMap.containsKey(id);
    }

//    методы для имитирования генерации филдов бдхой
    protected void postSaveProcessEntity(T entity){
        throw new UnsupportedOperationException("postSaveProcessEntity is not implemented yet");
    };
    protected void postUpdateProcessEntity(T entity){
        throw new UnsupportedOperationException("postUpdateProcessEntity is not implemented yet");
    };
    protected ID idGenNext(){
        throw new UnsupportedOperationException("idGenNext is not implemented yet");
    }

//    метод для искусственного заполнения
    protected void bulkInit(List<T> entities) {
        entities.forEach(e -> idMap.put(e.getId(), e));
        counter += entities.size() + 1;
        currentId = counter;
    }
}

//new UserRepo<UserEntity, Long>
