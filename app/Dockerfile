FROM ghcr.io/graalvm/native-image:ol7-java17-22.3.0 AS graalvm
ARG CERT="/tmp/rds-ca-2019-root.pem"
RUN curl -sS https://s3.amazonaws.com/rds-downloads/rds-ca-2019-root.pem -o $CERT
RUN /usr/lib64/graalvm/graalvm22-ce-java17/bin/keytool -importcert -trustcacerts -cacerts -file $CERT -alias $CERT -storepass changeit -noprompt
RUN rm -rf /tmp/*
WORKDIR /home/app
COPY build/docker/native-main/layers/libs /home/app/libs
COPY build/docker/native-main/layers/classes /home/app/classes
COPY build/docker/native-main/layers/resources /home/app/resources
COPY build/docker/native-main/layers/application.jar /home/app/application.jar
RUN mkdir /home/app/config-dirs
COPY build/docker/native-main/config-dirs/generateResourcesConfigFile /home/app/config-dirs/generateResourcesConfigFile
RUN native-image -cp /home/app/libs/*.jar:/home/app/resources:/home/app/application.jar --no-fallback -H:Name=application -J--add-exports=org.graalvm.nativeimage.builder/com.oracle.svm.core.configure=ALL-UNNAMED -J--add-exports=org.graalvm.nativeimage.builder/com.oracle.svm.core.jdk=ALL-UNNAMED -J--add-exports=org.graalvm.nativeimage.builder/com.oracle.svm.core.jni=ALL-UNNAMED -J--add-exports=org.graalvm.sdk/org.graalvm.nativeimage.impl=ALL-UNNAMED -H:ConfigurationFileDirectories=/home/app/config-dirs/generateResourcesConfigFile -H:Class=com.cryoport.skytrax.FunctionLambdaRuntime
FROM frolvlad/alpine-glibc:alpine-3.12
RUN apk --no-cache update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/application /app/application
ENTRYPOINT ["/app/application", "-XX:MaximumHeapSizePercent=80", "-Dio.netty.allocator.numDirectArenas=0", "-Dio.netty.noPreferDirect=true"]
