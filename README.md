# Jenkins JCasC

Jenkins Configuration as Code (JCasC) with Docker integration.

---
## Justification

This project demonstrates how to implement Jenkins Configuration as Code (JCasC) with Docker integration, focusing on building applications within a Docker image that will serve as a Jenkins slave on invoked nodes. Additionally, it incorporates Docker in Docker (DinD) to enable Jenkins agents to run Docker commands, creating a versatile and scalable CI/CD environment.

The project is structured around a Jenkins master configured entirely through JCasC, which is defined in a *casc.yaml* file. This approach automates the Jenkins setup process, allowing for quick and consistent configuration across different environments. The Jenkins master is containerized using a Dockerfile that also handles the installation of required plugins.

The project can be orchestrated using Docker Compose, which simplifies the management of Jenkins master container. This setup is ideal for scenarios where applications need to be built and tested in isolated environments, ensuring consistency and repeatability across builds.

---
## Requirements

* Docker 27.1.2
* Make 3.81

---
# Run

```bash
make docker.start
```

**Note: Set the local environment variable 'GIT_TOKEN' with your git token to configure in Jenkins using JCasC 'casc.yaml' file**

---
# Stop

```bash
make docker.start
```