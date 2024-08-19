package com.eightaugusto.tool

import com.eightaugusto.constant.PipelineConstant
import com.eightaugusto.util.StringUtil

final class Gradle {

  static String version(def pipeline, Map config) {
    return pipeline.sh(
        script: "${getBaseCmd(config)} properties --project-dir ${config[PipelineConstant.APPLICATION_BUILD_PATH_KEY]} --quiet | grep \"version:\" | awk '{print \$2}'",
        returnStdout: true
    ).trim()
  }

  static boolean isCompatible(def pipeline, Map config) {
    return pipeline.fileExists("${config[PipelineConstant.APPLICATION_BUILD_PATH_KEY]}/build.gradle")
  }

  static void unitTest(def pipeline, Map config) {
    final String gradleUnitTestCmd = [
        getBaseCmd(config),
        config.getOrDefault(PipelineConstant.GRADLE_UNIT_TEST_TASK_CONFIG_KEY, "clean test"),
        "--project-dir ${config[PipelineConstant.APPLICATION_BUILD_PATH_KEY]}",
        config.getOrDefault(PipelineConstant.GRADLE_UNIT_TEST_ARGS_CONFIG_KEY, pipeline.env.GRADLE_UNIT_TEST_DEFAULT_ARGS)
    ].findAll({ StringUtil.isNotBlank(it as String) }).join(StringUtil.WHITESPACE)
    pipeline.println("Executing Gradle Unit Test Command: '${gradleUnitTestCmd}'")
    pipeline.sh(gradleUnitTestCmd)
  }

  static void build(def pipeline, Map config) {
    final String gradleBuildCmd = [
        getBaseCmd(config),
        config.getOrDefault(PipelineConstant.GRADLE_BUILD_TASK_CONFIG_KEY, "clean build"),
        "--project-dir ${config[PipelineConstant.APPLICATION_BUILD_PATH_KEY]}",
        config.getOrDefault(PipelineConstant.GRADLE_BUILD_ARGS_CONFIG_KEY, pipeline.env.GRADLE_BUILD_DEFAULT_ARGS)
    ].findAll({ StringUtil.isNotBlank(it as String) }).join(StringUtil.WHITESPACE)
    pipeline.println("Executing Gradle Build Command: '${gradleBuildCmd}'")
    pipeline.sh(gradleBuildCmd)
  }

  private static String getBaseCmd(Map config) {
    return config.getOrDefault(PipelineConstant.GRADLE_CMD_KEY, "gradle")
  }

}