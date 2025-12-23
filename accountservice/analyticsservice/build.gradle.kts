plugins {
    java
    id("org.springframework.boot") version "3.4.1" // Downgrade here
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
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
    // 1. Corrected Web Starter
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":"))
    // 2. Standard Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    // 3. Swagger/OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    
    // 4. Axon
    implementation("org.axonframework:axon-spring-boot-starter:4.10.3")
    
    // 5. Utilities & Drivers
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    // Use developmentOnly for H2 console tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.mysql:mysql-connector-j")	
    // 6. Corrected Test Starters
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.withType<Test> {
	useJUnitPlatform()
}
