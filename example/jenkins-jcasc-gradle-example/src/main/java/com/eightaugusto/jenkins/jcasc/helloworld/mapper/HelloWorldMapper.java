package com.eightaugusto.jenkins.jcasc.helloworld.mapper;

import com.eightaugusto.jenkins.jcasc.helloworld.dto.HelloWorldDto;
import com.eightaugusto.jenkins.jcasc.helloworld.entity.HelloWorld;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** HelloWorldMapper. */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HelloWorldMapper {

  /**
   * Entity to Dto.
   *
   * @param entity Entity.
   * @return Dto.
   */
  HelloWorldDto convert(HelloWorld entity);
}
