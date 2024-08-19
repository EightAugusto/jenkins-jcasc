package com.eightaugusto.tool

import com.eightaugusto.constant.PipelineConstant
import com.eightaugusto.util.StringUtil

import javax.net.ssl.SSLException

final class Docker {

  static boolean isCompatible(def pipeline, Map config) {
    return pipeline.fileExists(getDockerFilePath(config))
  }

  static void publish(def pipeline, Map config) {
    // Docker Registry
    final String dockerRegistry = pipeline.env.DOCKER_REGISTRY as String
    if (StringUtil.isBlank(dockerRegistry)) {
      pipeline.error("DOCKER_REGISTRY Global Property variable should be defined")
    }
    // Docker base tag
    String dockerBaseTag = pipeline.env.DOCKER_REGISTRY
    if (!StringUtil.isBlank(pipeline.env.DOCKER_BUILD_GROUP as String)) {
      dockerBaseTag = "${dockerBaseTag}/${pipeline.env.DOCKER_BUILD_GROUP}"
    }
    dockerBaseTag = "${dockerBaseTag}/${config[PipelineConstant.APPLICATION_KEY]}"
    // Application Version
    final String applicationVersion = pipeline.env.APPLICATION_VERSION as String
    if (StringUtil.isBlank(applicationVersion)) {
      pipeline.error("APPLICATION_VERSION should be defined")
    }
    // Docker Build Architecture
    if (StringUtil.isBlank(pipeline.env.DOCKER_BUILD_ARCH as String)) {
      pipeline.error("DOCKER_BUILD_ARCH Global Property variable should be defined")
    }
    final String[] dockerBuildArchArray = ((pipeline.env.DOCKER_BUILD_ARCH as String).split(StringUtil.COMMA)) as String[]

    // Docker login
    pipeline.sh('echo ${DOCKER_CREDENTIALS_PSW} | docker login ${DOCKER_REGISTRY} --username ${DOCKER_CREDENTIALS_USR} --password-stdin')
    // Docker Build
    pipeline.println("Building ${dockerBaseTag}:${applicationVersion} for architectures '${dockerBuildArchArray}'")
    String[] dockerImages = new String[dockerBuildArchArray.size()]
    dockerBuildArchArray.eachWithIndex { buildArch, index ->
      String dockerImage = "${dockerBaseTag}:${applicationVersion}-${buildArch.replaceAll('/', '-')}"
      pipeline.sh(["docker build",
                   "-f ${getDockerFilePath(config)}",
                   "--tag ${dockerImage}",
                   "--platform ${buildArch}",
                   config[PipelineConstant.APPLICATION_BUILD_PATH_KEY],
                   pipeline.env.DOCKER_BUILD_ARGS
      ].findAll({ StringUtil.isNotBlank(it as String) }).join(StringUtil.WHITESPACE))
      pipeline.sh("docker push ${dockerImage}")
      dockerImages[index] = dockerImage
    }
    // Docker Manifests
    final String dockerInsecureManifestArg = isInsecureRegistry(pipeline) ? "--insecure" : null
    final String dockerAmendManifestArg = dockerImages.collect({ it -> "--amend ${it}" }).join(StringUtil.WHITESPACE)
    for (String dockerVersion in [applicationVersion, "latest"]) {
      pipeline.sh("docker manifest rm ${dockerBaseTag}:${dockerVersion} || true")
      pipeline.sh(["docker manifest create",
                   "${dockerBaseTag}:${dockerVersion}",
                   dockerAmendManifestArg,
                   dockerInsecureManifestArg
      ].findAll({ StringUtil.isNotBlank(it as String) }).join(StringUtil.WHITESPACE))
      pipeline.sh(["docker manifest push",
                   "${dockerBaseTag}:${dockerVersion}",
                   dockerInsecureManifestArg
      ].findAll({ StringUtil.isNotBlank(it as String) }).join(StringUtil.WHITESPACE))
    }
  }

  private static String getDockerFilePath(Map config) {
    return config.getOrDefault(
        // Get the configured docker file path
        PipelineConstant.DOCKER_FILE_PATH_CONFIG_KEY,
        // Or get the 'Dockerfile' from the default build path
        "${config[PipelineConstant.APPLICATION_BUILD_PATH_KEY]}/Dockerfile"
    )
  }

  private static boolean isInsecureRegistry(def pipeline) {
    try {
      new URI("https://${pipeline.env.DOCKER_REGISTRY}").toURL().openConnection().getResponseCode()
    }
    catch (SSLException ignored) {
      return true
    }
    return false
  }

}