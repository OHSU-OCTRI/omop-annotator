pipeline {
  agent any
  options {
    quietPeriod(120)
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }
  environment {
    DEPLOYMENT_FILE = 'k8s/dev/'
  }
  triggers {
    // Needed to trigger builds from Bitbucket
    pollSCM('')
  }
  tools {
    maven '3.5.0'
    jdk 'JDK11'
  }
  stages {
    stage('Prepare') {
      steps {
        checkout scm
      }
    }
    stage('Build') {
      steps {
        octriArtifactoryBuild(env.BRANCH_NAME)
      }
    }
    stage('Build Docker image') {
      when {
        branch 'master'
      }
      steps {
        mavenDockerMetadata(env)
        buildProjectDockerImage(env.IMAGE_NAME)
      }
    }
    stage('Deploy latest build') {
      when {
        branch 'master'
      }
      steps {
        deployLatestBuild(env.DEPLOYMENT_FILE)
      }
    }
  }
  post {
    // TODO: Uncomment when there are tests
    // always {
    //   junit 'target/surefire-reports/**/*.xml'
    // }
    unsuccessful {
      emailStatusChange()
    }
    fixed {
      emailStatusChange()
    }
  }
}
