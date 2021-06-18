pipeline {
  agent any
  stages {
    stage('Pull') {
      steps {
        git(url: 'https://git.squarecode.de/SquareCode/riotspigot', branch: 'master', credentialsId: 'git:ac6018e9c555f11568c341be17232522f81a4aa26f377b56a44af8d565adb4c0', changelog: true, poll: true)
      }
    }

    stage('Build') {
      steps {
        sh '/opt/maven-3.8.1/bin/mvn -B -DskipTests clean package'
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts(artifacts: 'riotspigot-server/target/riotspigot.jar', onlyIfSuccessful: true)
      }
    }

  }
  tools {
    maven 'maven'
    jdk 'Java8'
  }
}