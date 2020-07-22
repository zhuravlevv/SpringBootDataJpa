package com.lessons.service.mapper;

public interface Mapper <Entity, Dto>{

    public Entity toEntity(Dto dto);

    public Dto fromEntity(Entity entity);
}
