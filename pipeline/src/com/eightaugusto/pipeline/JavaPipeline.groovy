package com.eightaugusto.pipeline

import com.eightaugusto.tool.Docker
import com.eightaugusto.tool.Gradle
import com.eightaugusto.tool.Maven

final class JavaPipeline extends BasePipeline {

  @Override
  String getVersion(def pipeline, Map config) {
    if (Maven.isCompatible(pipeline, config)) {
      return Maven.version(pipeline, config)
    } else if (Gradle.isCompatible(pipeline, config)) {
      return Gradle.version(pipeline, config)
    } else {
      pipeline.error("Could not detect a compatible build tool the current project")
    }
  }

  @Override
  void unitTest(def pipeline, Map config) {
    if (Maven.isCompatible(pipeline, config)) {
      Maven.unitTest(pipeline, config)
    } else if (Gradle.isCompatible(pipeline, config)) {
      Gradle.unitTest(pipeline, config)
    } else {
      pipeline.error("Could not detect a compatible build tool the current project")
    }
  }

  @Override
  void build(def pipeline, Map config) {
    if (Maven.isCompatible(pipeline, config)) {
      Maven.build(pipeline, config)
    } else if (Gradle.isCompatible(pipeline, config)) {
      Gradle.build(pipeline, config)
    } else {
      pipeline.error("Could not detect a compatible build tool the current project")
    }
  }

  @Override
  void publish(def pipeline, Map config) {
    if (Docker.isCompatible(pipeline, config)) {
      Docker.publish(pipeline, config)
    }
  }

}