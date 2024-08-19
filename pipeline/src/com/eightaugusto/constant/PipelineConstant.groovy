package com.eightaugusto.constant

// All the pipeline constants for pipeline definition
interface PipelineConstant {

  // Application type
  static enum ApplicationType {
    JAVA
  }
  // Pipeline type properties used in all the pipelines
  public static final String APPLICATION_KEY = "application"
  public static final String APPLICATION_TYPE = "applicationType"
  public static final String APPLICATION_BUILD_PATH_KEY = "applicationBuildPath"
  // Maven pipeline properties used in com.eightaugusto.tool.Maven
  public static final String MVN_UNIT_TEST_GOALS_CONFIG_KEY = "mvnUnitTestGoals"
  public static final String MVN_UNIT_TEST_ARGS_CONFIG_KEY = "mvnUnitTestArgs"
  public static final String MVN_BUILD_GOALS_CONFIG_KEY = "mvnBuildGoals"
  public static final String MVN_BUILD_ARGS_CONFIG_KEY = "mvnBuildArgs"
  // Gradle pipeline properties used in com.eightaugusto.tool.Gradle
  public static final String GRADLE_CMD_KEY = "gradleCmd"
  public static final String GRADLE_UNIT_TEST_TASK_CONFIG_KEY = "gradleUnitTestGoals"
  public static final String GRADLE_UNIT_TEST_ARGS_CONFIG_KEY = "gradleUnitTestArgs"
  public static final String GRADLE_BUILD_TASK_CONFIG_KEY = "gradleBuildGoals"
  public static final String GRADLE_BUILD_ARGS_CONFIG_KEY = "gradleBuildArgs"
  // Docker pipeline properties used in com.eightaugusto.tool.Docker
  public static final String DOCKER_FILE_PATH_CONFIG_KEY = "dockerFilePath"

}
