FROM docker.io/jenkins/jenkins:2.474-jdk21

USER root

# Docker CE
RUN apt-get update; \
    apt-get install -y curl gnupg ca-certificates lsb-release; \
    update-ca-certificates; \
    curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg; \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null; \
    apt-get update; \
    apt-get install -y docker-ce; \
    usermod -aG docker jenkins;

ENV JAVA_OPTS=-Djenkins.install.runSetupWizard=false

RUN jenkins-plugin-cli --plugins \
    antisamy-markup-formatter:162.v0e6ec0fcfcf6 \
    build-timeout:1.33 \
    configuration-as-code:1850.va_a_8c31d3158b_ \
    credentials-binding:681.vf91669a_32e45 \
    credentials:1371.vfee6b_095f0a_3 \
    docker-commons:443.v921729d5611d \
    docker-plugin:1.6.2 \
    docker-workflow:580.vc0c340686b_54 \
    github-branch-source:1797.v86fdb_4d57d43 \
    matrix-auth:3.2.2 \
    pam-auth:1.11 \
    pipeline-github-lib:61.v629f2cc41d83 \
    pipeline-stage-view:2.34 \
    pipeline-utility-steps:2.17.0 \
    ssh-slaves:2.973.v0fa_8c0dea_f9f \
    timestamper:1.27 \
    workflow-aggregator:600.vb_57cdd26fdd7 \
    workflow-cps:3953.v19f11da_8d2fa_ \
    ws-cleanup:0.46