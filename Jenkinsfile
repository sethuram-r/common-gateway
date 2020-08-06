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
        stage('Build Docker Image') {
            steps {
                script {
                    docker.withRegistry("https://registry.hub.docker.com", "dockerhub") {
                        def image = docker.build("sethuram975351/gateway:latest")
                        image.push()
                    }
                }
            }
        }
//       stage('Kubernetes Deployment') {
//               steps {
//                 sh 'kubectl apply -f gateway-deployment.yaml'
//               }
//       }
    }
}