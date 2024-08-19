package com.eightaugusto.pipeline

import com.eightaugusto.constant.PipelineConstant

final class PipelineFactory {

  static BasePipeline getPipeline(def pipeline, Map config) {
    pipeline.println("Getting pipeline implementation using the configuration: '${config}'")
    if (!config.containsKey(PipelineConstant.APPLICATION_TYPE)) {
      pipeline.error("Configuration '${PipelineConstant.APPLICATION_TYPE}' not found")
    }

    switch ((config[PipelineConstant.APPLICATION_TYPE] as String).toUpperCase()) {
      case PipelineConstant.ApplicationType.JAVA.name(): return new JavaPipeline()
    }

    pipeline.error("Invalid Application Type '${PipelineConstant.APPLICATION_TYPE}'")
  }

}