pipeline {
  agent any
  options {
    quietPeriod(30)
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }
  environment {
    DEFAULT_BRANCH = 'main'
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
        octriMavenBuild(deployArtifacts: env.BRANCH_NAME == env.DEFAULT_BRANCH)
      }
    }
    stage('Test JS') {
      steps {
        script {
          def args = "-e TZ=America/Los_Angeles -v ${WORKSPACE}:/app"
          def cmd = "npm run test-jenkins"
          docker.image("octri.ohsu.edu/node_test_chrome:latest").withRun(args, cmd) {c ->
            timeout(time: 600, unit: 'SECONDS') {
              sh "docker wait ${c.id}"
            }
          }
        }
      }
    }
    stage('Build Docker image') {
      when {
        branch env.DEFAULT_BRANCH
      }
      steps {
        mavenDockerMetadata(env)
        buildProjectDockerImage(env.IMAGE_NAME)
      }
    }
    stage('Deploy latest build') {
      when {
        branch env.DEFAULT_BRANCH
      }
      steps {
        deployLatestBuild(env.DEPLOYMENT_FILE)
      }
    }
  }
  post {
    always {
      junit 'target/surefire-reports/**/*.xml'
    }
    unsuccessful {
      emailStatusChange()
    }
    fixed {
      emailStatusChange()
    }
  }
}
