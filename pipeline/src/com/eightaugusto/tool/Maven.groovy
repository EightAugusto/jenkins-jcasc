package com.eightaugusto.tool

import com.eightaugusto.constant.PipelineConstant
import com.eightaugusto.util.StringUtil

final class Maven {

  static boolean isCompatible(def pipeline, Map config) {
    return pipeline.fileExists(getBuildFilePath(config))
  }

  static String version(def pipeline, Map config) {
    return pipeline.sh(
        script: "mvn help:evaluate -Dexpression=project.version --quiet -DforceStdout --file ${getBuildFilePath(config)}",
        returnStdout: true
    ).trim()
  }

  static void unitTest(def pipeline, Map config) {
    final String mvnUnitTestCmd = [
        "mvn",
        config.getOrDefault(PipelineConstant.MVN_UNIT_TEST_GOALS_CONFIG_KEY, "clean build"),
        "--file ${getBuildFilePath(config)}",
        config.getOrDefault(PipelineConstant.MVN_UNIT_TEST_ARGS_CONFIG_KEY, pipeline.env.MVN_UNIT_TEST_DEFAULT_ARGS)
    ].findAll({ StringUtil.isNotBlank(it as String) }).join(StringUtil.WHITESPACE)
    pipeline.println("Executing Maven Unit Test Command: '${mvnUnitTestCmd}'")
    pipeline.sh(mvnUnitTestCmd)
  }

  static void build(def pipeline, Map config) {
    final String mvnBuildCmd = [
        "mvn",
        config.getOrDefault(PipelineConstant.MVN_BUILD_GOALS_CONFIG_KEY, "clean test"),
        "--file ${getBuildFilePath(config)}",
        config.getOrDefault(PipelineConstant.MVN_BUILD_ARGS_CONFIG_KEY, pipeline.env.MVN_BUILD_DEFAULT_ARGS)
    ].findAll({ StringUtil.isNotBlank(it as String) }).join(StringUtil.WHITESPACE)
    pipeline.println("Executing Maven Build Command: '${mvnBuildCmd}'")
    pipeline.sh(mvnBuildCmd)
  }

  private static String getBuildFilePath(Map config) {
    return "${config[PipelineConstant.APPLICATION_BUILD_PATH_KEY]}/pom.xml"
  }

}