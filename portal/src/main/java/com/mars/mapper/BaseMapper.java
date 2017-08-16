package com.mars.mapper;

import java.util.List;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 *
 * Base interface for all mappers
 */
public interface BaseMapper<Entity, Model>
{

    /**
     * @Brief Converts single Entity to Model
     * @param entity <code>Entity</code> to convert
     * @return <code>Model</code> instance based on passed <code>Entity</code>
     */
    Model toModel(Entity entity);

    /**
     * @Brief Converts entities to models
     * @param entities Iterable of <code>Entity<code/> objects to convert
     * @return List of <code>Model</code> objects based on passed list of <code>Entity<code/> objects
     */
    List<Model> toModels(Iterable<Entity> entities);

    /**
     * @Brief Converts single Entity to Model
     * @param model <code>Entity</code> to convert
     * @return <code>Model</code> instance based on passed <code>Entity</code>
     */
    Entity toEntity(Model model);

    /**
     * @Brief Converts models to entities
     * @param models Iterable of <code>Model<code/> objects to convert
     * @return List of <code>Entity</code> objects based on passed list of <code>Model<code/> objects
     */
    List<Entity> toEntities(Iterable<Model> models);

}
