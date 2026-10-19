FROM registry.fedoraproject.org/fedora:44
RUN sudo dnf install -y adoptium-temurin-java-repository
RUN sudo dnf --enablerepo=adoptium-temurin-java-repository install -y temurin-17-jdk 
RUN curl -L -o commandlinetools.zip https://dl.google.com/android/repository/commandlinetools-linux-15859902_latest.zip
RUN sudo dnf install -y unzip
ENV JAVA_HOME=/usr/lib/jvm/java-17-temurin-jdk/
ENV ANDROID_HOME=/opt/android-sdk
RUN mkdir -p /opt/android-sdk
RUN mv commandlinetools.zip /opt/android-sdk 
WORKDIR /opt/android-sdk
RUN unzip commandlinetools.zip
WORKDIR /opt/android-sdk/cmdline-tools/bin
RUN yes | ./sdkmanager --sdk_root=/opt/android-sdk --licenses
RUN ./sdkmanager --sdk_root=/opt/android-sdk "platforms;android-34" "build-tools;34.0.0" "platform-tools"
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:/opt/android-sdk/cmdline-tools/bin:/opt/android-sdk/platform-tools

WORKDIR /root 
RUN android
RUN android create --name="HelloWorld" --application-id=com.example.helloworld --output=./hello-world

# RUN useradd -m -u 1000 container-user
# USER container-user
# WORKDIR /container-workspace

# /opt/android-sdk/cmdline-tools/latest/bin

# from: https://developer.android.com/studio#command-line-tools-only
# https://dl.google.com/android/repository/commandlinetools-linux-15859902_latest.zip

# /usr/lib/jvm/java-17-temurin-jdk/bin/java
# export JAVA_HOME=/usr/lib/jvm/java-17-temurin-jdk/
# RUN export JAVA_HOME=$(readlink -f $(command -v java))

# RUN dnf install -y \
#     && dnf clean all