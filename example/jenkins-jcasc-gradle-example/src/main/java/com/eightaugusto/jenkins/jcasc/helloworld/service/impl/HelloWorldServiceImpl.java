package com.eightaugusto.jenkins.jcasc.helloworld.service.impl;

import com.eightaugusto.jenkins.jcasc.helloworld.dto.HelloWorldDto;
import com.eightaugusto.jenkins.jcasc.helloworld.entity.HelloWorld;
import com.eightaugusto.jenkins.jcasc.helloworld.mapper.HelloWorldMapper;
import com.eightaugusto.jenkins.jcasc.helloworld.service.HelloWorldService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** HelloWorldServiceImpl. */
@Log4j2
@Service
public class HelloWorldServiceImpl implements HelloWorldService {

  private static final String HELLO_WORLD = "Hello World";

  private final HelloWorldMapper mapper;
  private final HelloWorld entity;

  public HelloWorldServiceImpl(HelloWorldMapper mapper) {
    this.mapper = mapper;
    this.entity = new HelloWorld(HELLO_WORLD);
  }

  @Override
  public HelloWorldDto getHelloWorld() {
    log.traceEntry("({})");
    return log.traceExit(mapper.convert(this.entity));
  }
}
