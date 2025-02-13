package org.example.msenfant.Service;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public abstract class GeneriqueServiceImpl<Request, Response, Entity> implements IGeneriqueService<Request, Response> {


    protected CrudRepository<Entity, String> repository;

    public GeneriqueServiceImpl(CrudRepository<Entity, String> repository) {
        this.repository = repository;
    }

    protected abstract Response mapToResponse(Entity entity);

    protected abstract Entity mapToEntity(Request dto);

    public abstract String getEntityName();

    @Override
    public List<Response> getAllEntities() {
        List<Entity> entities = (List<Entity>) repository.findAll();
        return entities.stream().map(this::mapToResponse).toList();
    }

    @Override
    public Response getEntityById(String id) {
        Entity entity = repository.findById(id).orElseThrow(() -> new RuntimeException(getEntityName() + " avec l'id: " + id + " n a pas etait trouver"));
        return mapToResponse(entity);
    }

    @Override
    public Response createEntity(Request dto) {
        Entity entity = mapToEntity(dto);
        Entity savedEntity = repository.save(entity);
        return mapToResponse(savedEntity);
    }

    @Override
    public Response updateEntity(String id, Request dto) {
        Entity existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(getEntityName() + " avec l'id: " + id + " n'a pas été trouvé"));

        updateEntityFields(existingEntity, dto);

        Entity savedEntity = repository.save(existingEntity);

        return mapToResponse(savedEntity);
    }

    protected void updateEntityFields(Entity entity, Request dto) {
        try {
            for (var fieldDto : dto.getClass().getDeclaredFields()) {
                fieldDto.setAccessible(true);

                Object value = fieldDto.get(dto);

                if (value != null) {

                    var fieldEntity = entity.getClass().getDeclaredField(fieldDto.getName());
                    fieldEntity.setAccessible(true);
                    fieldEntity.set(entity, value);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Erreur lors de la mise à jour des champs : " + e.getMessage(), e);
        }
    }


    @Override
    public void deleteEntity(String id) {
        Entity entity = repository.findById(id).orElseThrow(() -> new RuntimeException(getEntityName() + " avec l'id: " + id + " n a pas etait trouver"));
        repository.delete(entity);
    }
}
