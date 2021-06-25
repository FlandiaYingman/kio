plugins {
    kotlin("jvm") version "1.5.10"

    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.dokka") version "1.4.32"
}

group = "top.anagke"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0-M1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.named("dokkaJavadoc"))
}

artifacts {
    archives(tasks.named("javadocJar"))
    archives(tasks.named("kotlinSourcesJar"))
}

java {
    withSourcesJar()
    withJavadocJar()
}

val mavenUsername: String = project.findProperty("mavenCentralRepositoryUsername").toString()
val mavenPassword: String = project.findProperty("mavenCentralRepositoryPassword").toString()

publishing {
    publications {
        create<MavenPublication>("mavenCentral") {
            from(components["java"])

            pom {
                name.set("kio")
                description.set("A simple Kotlin IO library that targets to simplify File IO operations.")
                url.set("https://github.com/FlandiaYingman/kio")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("Flandia_Yingman")
                        name.set("Flandia Yingman")
                        email.set("Flandia_YingM@hotmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/FlandiaYingman/kio.git")
                    url.set("https://github.com/FlandiaYingman/kio")
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = mavenUsername
                password = mavenPassword
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenCentral"])
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(mavenUsername)
            password.set(mavenPassword)
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

tasks.register("release") {
    group = "publishing"

    dependsOn(tasks.named("publishToSonatype"))
    dependsOn(tasks.named("closeAndReleaseSonatypeStagingRepository"))
}