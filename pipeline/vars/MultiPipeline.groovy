import com.eightaugusto.constant.PipelineConstant
import com.eightaugusto.pipeline.BasePipeline
import com.eightaugusto.pipeline.PipelineFactory

def call(body) {

  // Evaluate the body block and collect configuration into the object
  Map config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  // Set the default variables for the execution
  if (!config.containsKey(PipelineConstant.APPLICATION_BUILD_PATH_KEY)) {
    // Default application build path is root
    config[PipelineConstant.APPLICATION_BUILD_PATH_KEY] = "."
  }
  if (!config.containsKey(PipelineConstant.APPLICATION_KEY)) {
    // Default application name is based on the current job name
    config[PipelineConstant.APPLICATION_KEY] = env.JOB_NAME.tokenize('/')[0]
  }
  // Get the current pipeline implementation
  final BasePipeline basePipeline = PipelineFactory.getPipeline(this, config)

  pipeline {

    agent { label env.DOCKER_AGENT_LABEL }
    parameters {
      booleanParam(name: "MANUAL_BUILD_PUBLISH", defaultValue: false, description: "Trigger a manual build/publish")
    }
    environment {
      DOCKER_CREDENTIALS = credentials("DOCKER_CREDENTIALS")

      IS_DEV_ENV = "${(env.BRANCH_NAME == 'dev' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'development')}"
      IS_STAGE_ENV = "${env.BRANCH_NAME =~ 'hotfix.*|release.*'}"
      IS_PROD_ENV = "${(env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'master')}"
    }

    stages {
      stage("Version") { steps { script { env.APPLICATION_VERSION = basePipeline.getVersion(this, config) } } }
      stage("Unit Test") { steps { script { basePipeline.unitTest(this, config) } } }
      stage("Build") {
        when { expression { env.IS_DEV_ENV.toBoolean() || env.IS_STAGE_ENV.toBoolean() || env.IS_PROD_ENV.toBoolean() || params.MANUAL_BUILD_PUBLISH.toBoolean() } }
        steps { script { basePipeline.build(this, config) } }
      }
      stage("Publish") {
        when { expression { env.IS_DEV_ENV.toBoolean() || env.IS_STAGE_ENV.toBoolean() || env.IS_PROD_ENV.toBoolean() || params.MANUAL_BUILD_PUBLISH.toBoolean() } }
        steps { script { basePipeline.publish(this, config) } }
      }
    }

  }

}