pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://maven.google.com/")
        maven("https://jcenter.bintray.com/")
        maven("https://repo1.maven.org/maven2/")
        maven("https://mvn.mchv.eu/repository/mchv/")
        maven("https://dl.bintray.com/bilibili/maven/")
        maven("https://repo.spring.io/libs-milestone/")
        maven("https://repo.spring.io/plugins-release/")
        maven("https://maven.java.net/content/groups/public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://repository.apache.org/content/repositories/snapshots/")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.google.com/")
        maven("https://jcenter.bintray.com/")
        maven("https://repo1.maven.org/maven2/")
        maven("https://mvn.mchv.eu/repository/mchv/")
        maven("https://dl.bintray.com/bilibili/maven/")
        maven("https://repo.spring.io/libs-milestone/")
        maven("https://repo.spring.io/plugins-release/")
        maven("https://maven.java.net/content/groups/public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://repository.apache.org/content/repositories/snapshots/")
    }
}

include(":app")
rootProject.name = "APPUpdater"