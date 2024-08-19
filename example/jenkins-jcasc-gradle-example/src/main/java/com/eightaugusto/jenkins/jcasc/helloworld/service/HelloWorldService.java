package com.eightaugusto.jenkins.jcasc.helloworld.service;

import com.eightaugusto.jenkins.jcasc.helloworld.dto.HelloWorldDto;

/** HelloWorldService. */
public interface HelloWorldService {

  /**
   * Get <code>HelloWorldDto</code>.
   *
   * @return HelloWorldDto.
   */
  HelloWorldDto getHelloWorld();
}
