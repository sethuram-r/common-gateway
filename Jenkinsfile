pipeline {
    agent any

    tools {
        maven "maven 3.5.2"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Tools Check') {
            steps {
                sh 'mvn -v'
            }
        }
        stage('Build Package') {
            steps {
                sh 'mvn clean install -DskipTests -Pproduction'
            }
        }
        stage('Build and push Docker Image') {
            steps {
                script {
                    docker.withRegistry("https://registry.hub.docker.com", "dockerhub") {
                        def image = docker.build("sethuram975351/gateway:${env.BUILD_ID}")
                        image.push()
                    }
                }
            }
        }
    }
}