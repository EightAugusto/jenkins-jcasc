include .env

docker.build:
	@echo "Building Jenkins JCasC Master Docker Image"
	@docker build --tag ${DOCKER_JENKINS_MASTER_IMAGE_TAG} --file ./docker/master/Dockerfile .
	@echo "Building Jenkins JCasC Slaves Docker Image"
	@docker build --tag ${DOCKER_JENKINS_SLAVE_IMAGE_TAG} --file ./docker/slave/Dockerfile .

docker.network:
	@echo "Ensure docker network: ${DOCKER_NETWORK}"
	@docker network inspect ${DOCKER_NETWORK} >/dev/null 2>&1 || docker network create --driver bridge ${DOCKER_NETWORK}

docker.start: docker.build docker.network
	@echo "Starting the application: ${APPLICATION}"
	@GIT_TOKEN=${GIT_TOKEN} docker compose --project-name ${APPLICATION} up --detach

docker.stop:
	@echo "Stopping the application: ${APPLICATION}"
	@docker remove --force $$(docker ps --filter "label=com.docker.compose.project=${APPLICATION}" --format '{{.ID}}')