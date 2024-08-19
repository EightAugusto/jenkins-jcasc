package com.eightaugusto.pipeline

abstract class BasePipeline {

  abstract String getVersion(def pipeline, Map config)

  abstract void unitTest(def pipeline, Map config);

  abstract void build(def pipeline, Map config);

  abstract void publish(def pipeline, Map config);

}