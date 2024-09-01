package com.senla.util.repository;

import java.io.Serializable;
import java.util.*;

public abstract class AbstractRepository<T extends Identifiable<ID>, ID extends Serializable> implements Repository<T, ID> {

    protected Map<ID, T> idMap = new HashMap<>();
    protected long counter = 0L;
    protected ID currentId;

    @Override
    public long count() {
        return counter;
    }

    @Override
    public T save(T entity) {
        ID idToSave = entity.getId();
        if(idToSave == null) {
            try{
                idToSave = idGenerateNext();

                postSaveProcessEntity(entity);
                entity.setId(idToSave);

                idMap.put(idToSave, entity);
                counter++;
            } catch (RuntimeException e) {
                degradeCurrentId();
                return null;
            }
        } else {
            try{
                if(!idMap.containsKey(idToSave)) throw new RuntimeException("Absent Id");
                postUpdateProcessEntity(entity);
                idMap.replace(idToSave, entity);
            } catch (RuntimeException e) {
                return null;
            }
        }
        return entity;
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

    protected abstract void postSaveProcessEntity(T entity);
    protected abstract void postUpdateProcessEntity(T entity);
    protected abstract void degradeCurrentId();
    protected abstract ID idGenerateNext();

//    метод для искусственного заполнения
    protected void bulkInit(List<T> entities) {
        entities.forEach(e -> idMap.put(e.getId(), e));
        counter += entities.size();
        currentId = entities.getLast().getId();
    }
}

//new UserRepo<UserEntity, Long>
