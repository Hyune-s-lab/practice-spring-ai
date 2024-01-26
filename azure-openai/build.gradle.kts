dependencies {
    implementation("org.springframework.ai:spring-ai-azure-openai-spring-boot-starter:0.8.0-SNAPSHOT")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
