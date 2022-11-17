import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	maven{ setUrl("https://maven.aliyun.com/nexus/content/groups/public/")}
	mavenCentral()
}

dependencies {
	//web
	implementation("org.springframework.boot:spring-boot-starter-web")
	//数据库
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("com.baomidou:mybatis-plus-boot-starter:3.5.2")
	implementation("mysql:mysql-connector-java")
	//基础环境
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	//默认把所有类设置open类的插件
	implementation("org.jetbrains.kotlin:kotlin-noarg")
	implementation("org.jetbrains.kotlin:kotlin-allopen")
	//密码
	implementation("cn.dev33:sa-token-spring-boot-starter:1.30.0")
	// json
	implementation("com.alibaba:fastjson:1.2.79")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
