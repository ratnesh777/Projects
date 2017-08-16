package com.mars.mapper;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseMapper<Entity, Model> implements BaseMapper<Entity, Model>
{
    @Override
    public List<Model> toModels(Iterable<Entity> entities)
    {
        List<Model> models = new ArrayList<>();
        if (entities != null)
        {
            for (Entity entity : entities)
            {
                models.add(toModel(entity));
            }
        }
        return models;
    }

    @Override
    public List<Entity> toEntities(Iterable<Model> models)
    {
        List<Entity> entities = new ArrayList<>();
        if (models != null)
        {
            for (Model model : models)
            {
                entities.add(toEntity(model));
            }
        }
        return entities;
    }
}
