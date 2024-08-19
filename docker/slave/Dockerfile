FROM docker.io/almalinux:9.4-minimal-20240723

ARG \
    # https://docs.docker.com/engine/release-notes
    DOCKER_VERSION="27.1.2" \
    # https://www.oracle.com/java/technologies/downloads/archive/
    JAVA_VERSION="21.0.4" \
    # https://maven.apache.org/docs/history.html
    MAVEN_VERSION="3.9.9" \
    # https://gradle.org/releases/
    GRADLE_VERSION="8.10" \
    # https://go.dev/dl/
    GO_VERSION="1.23.0"

ENV JENKINS_HOME="/home/jenkins" \
    JAVA_HOME="/opt/jdk-${JAVA_VERSION}.jdk" \
    M2_HOME="/opt/apache-maven-${MAVEN_VERSION}" \
    GRADLE_HOME="/opt/gradle-${GRADLE_VERSION}" \
    GO_HOME="/usr/local/go" \
    PATH=\
"${PATH}"\
":/opt/jdk-${JAVA_VERSION}.jdk/bin"\
":/opt/apache-maven-${MAVEN_VERSION}/bin"\
":/opt/gradle-${GRADLE_VERSION}/bin"\
":/usr/local/go/bin"

# Jenkins configuration
RUN mkdir -p ${JENKINS_HOME}; \
    ln -s ${JENKINS_HOME} /var/lib/jenkins;

# Required dependencies
RUN microdnf update -y; \
    microdnf install git tar gzip unzip findutils -y; \
    microdnf clean all;

# Docker CE
RUN case $(arch) in \
      "x86_64") OS_ARCH="x86_64";; \
      "aarch64"|"arm64") OS_ARCH="aarch64";; \
      *) echo "Unsuported architecture: $(arch)"; exit 1;; \
    esac; \
    curl --silent --fail --location --output docker-${DOCKER_VERSION}.tgz https://download.docker.com/linux/static/stable/${OS_ARCH}/docker-${DOCKER_VERSION}.tgz; \
    tar --extract --file docker-${DOCKER_VERSION}.tgz --directory /usr/bin --strip-components 1 --no-same-owner; \
    rm docker-${DOCKER_VERSION}.tgz; \
    if [ ! $(command -v docker) ]; then echo "Error when trying to install Docker"; exit 1; else docker --version; fi;

# Java
RUN mkdir -p ${JAVA_HOME}; \
    case $(arch) in \
      "x86_64") OS_ARCH="x64";; \
      "aarch64"|"arm64") OS_ARCH="aarch64";; \
      *) echo "Unsuported architecture: $(arch)"; exit 1;; \
    esac; \
    curl --silent --fail --output ${JAVA_HOME}/jdk-${JAVA_VERSION}_linux-${OS_ARCH}_bin.tar.gz https://download.oracle.com/java/${JAVA_VERSION%%.*}/archive/jdk-${JAVA_VERSION}_linux-${OS_ARCH}_bin.tar.gz; \
    curl --silent --fail --output ${JAVA_HOME}/jdk-${JAVA_VERSION}_linux-${OS_ARCH}_bin.tar.gz.sha256 https://download.oracle.com/java/${JAVA_VERSION%%.*}/archive/jdk-${JAVA_VERSION}_linux-${OS_ARCH}_bin.tar.gz.sha256; \
    cd ${JAVA_HOME}; \
    echo "$(cat jdk-${JAVA_VERSION}_linux-${OS_ARCH}_bin.tar.gz.sha256) ${JAVA_HOME}/jdk-${JAVA_VERSION}_linux-${OS_ARCH}_bin.tar.gz" | sha256sum --quiet --strict --check -; \ 
    rm jdk-${JAVA_VERSION}_linux-${OS_ARCH}_bin.tar.gz.sha256; \
    cd --; \
    tar --extract --file ${JAVA_HOME}/jdk-${JAVA_VERSION}_linux-${OS_ARCH}_bin.tar.gz --directory ${JAVA_HOME} --strip-components 1 --no-same-owner; \
    rm ${JAVA_HOME}/jdk-${JAVA_VERSION}_linux-${OS_ARCH}_bin.tar.gz; \
    if [ ! $(command -v java) ]; then echo "Error when trying to install Java"; exit 1; else java --version; fi;

# Maven
RUN mkdir -p ${M2_HOME}; \ 
    curl --silent --fail --output ${M2_HOME}/../apache-maven-${MAVEN_VERSION}-bin.tar.gz https://dlcdn.apache.org/maven/maven-${MAVEN_VERSION%%.*}/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz; \
    curl --silent --fail --output ${M2_HOME}/../apache-maven-${MAVEN_VERSION}-bin.tar.gz.sha512 https://dlcdn.apache.org/maven/maven-${MAVEN_VERSION%%.*}/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz.sha512; \
    cd ${M2_HOME}/..; \
    echo "$(cat apache-maven-${MAVEN_VERSION}-bin.tar.gz.sha512) apache-maven-${MAVEN_VERSION}-bin.tar.gz" | sha512sum --quiet --strict --check -; \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz.sha512; \
    cd --; \
    tar --extract --file ${M2_HOME}/../apache-maven-${MAVEN_VERSION}-bin.tar.gz --directory ${M2_HOME} --strip-components 1 --no-same-owner; \
    rm ${M2_HOME}/../apache-maven-${MAVEN_VERSION}-bin.tar.gz; \
    if [ ! $(command -v mvn) ]; then echo "Error when trying to install Maven"; exit 1; else mvn --version; fi;

# Gradle
RUN mkdir -p ${GRADLE_HOME}; \
    curl --silent --fail --output ${GRADLE_HOME}/../gradle-${GRADLE_VERSION}-bin.zip --location --remote-name --url https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip; \
    curl --silent --fail --output ${GRADLE_HOME}/../gradle-${GRADLE_VERSION}-bin.zip.sha256 --location --remote-name --url https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip.sha256; \
    cd ${GRADLE_HOME}/..; \
    echo "$(cat gradle-${GRADLE_VERSION}-bin.zip.sha256) gradle-${GRADLE_VERSION}-bin.zip" | sha256sum --quiet --strict --check -; \
    rm gradle-${GRADLE_VERSION}-bin.zip.sha256; \
    cd --; \
    unzip -qq ${GRADLE_HOME}/../gradle-${GRADLE_VERSION}-bin.zip -d ${GRADLE_HOME}/..; \
    rm ${GRADLE_HOME}/../gradle-${GRADLE_VERSION}-bin.zip; \
    if [ ! $(command -v gradle) ]; then echo "Error when trying to install Gradle"; exit 1; else gradle --version; fi;

# Go
RUN mkdir -p ${GO_HOME}; \
    case $(arch) in \
      "x86_64") OS_ARCH="x64";; \
      "aarch64"|"arm64") OS_ARCH="arm64";; \
      *) echo "Unsuported architecture: $(arch)"; exit 1;; \
    esac; \
    curl --silent --fail --output ${GO_HOME}/go${GO_VERSION}.linux-${OS_ARCH}.tar.gz https://dl.google.com/go/go${GO_VERSION}.linux-${OS_ARCH}.tar.gz; \
    curl --silent --fail --output ${GO_HOME}/go${GO_VERSION}.linux-${OS_ARCH}.tar.gz.sha256 https://dl.google.com/go/go${GO_VERSION}.linux-${OS_ARCH}.tar.gz.sha256; \
    cd ${GO_HOME}; \
    echo "$(cat go${GO_VERSION}.linux-${OS_ARCH}.tar.gz.sha256) go${GO_VERSION}.linux-${OS_ARCH}.tar.gz" | sha256sum --quiet --strict --check -; \
    rm go${GO_VERSION}.linux-${OS_ARCH}.tar.gz.sha256; \
    cd --; \
    tar --extract --file ${GO_HOME}/go${GO_VERSION}.linux-${OS_ARCH}.tar.gz --directory ${GO_HOME} --strip-components 1 --no-same-owner; \
    rm ${GO_HOME}/go${GO_VERSION}.linux-${OS_ARCH}.tar.gz; \
    if [ ! $(command -v go) ]; then echo "Error when trying to install Go"; exit 1; else go version; fi;