import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

tasks.jar {
    enabled = false
}

plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}
val springCloudVersion by extra("2023.0.1")
val testContainersVersion by extra("1.17.3") // 사용할 테스트 컨테이너 버전 지정

group = "com.yeonnex"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.flywaydb:flyway-core")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.testcontainers:postgresql") // Postgresql 데이터베이스에 대한 컨테이너 관리 기능 제공
    runtimeOnly("org.postgresql:postgresql")
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        mavenBom("org.testcontainers:testcontainers-bom:$testContainersVersion") // 테스트 컨테이너 의존성 관리를 위한 의존성 BOM
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


// Spring Boot 이미지 빌드 구성 --builder ghcr.io/thomasvitale/java-builder-arm64 인수 추가
tasks.getByName<BootBuildImage>("bootBuildImage") {
    imageName.set(project.name)
    environment = mapOf(
            "BP_JVM_VERSION" to "17.*"  // Java 버전을 문자열로 명시
    )
    builder.set("ghcr.io/thomasvitale/java-builder-arm64")
    /*publish = true // 이미지를 레지스트리에 푸시할 것인지 여부
    docker {
        publishRegistry {
            url.set("${project.findProperty("registryUrl")}")
            username.set("${project.findProperty("registryUsername")}")
            password.set("${project.findProperty("registryToken")}")
        }
    }*/

}
