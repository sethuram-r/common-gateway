pipeline {
   agent any

   stages {

      stage('Checkout') {
         steps {
                  checkout scm
               }
         }
      stage('Build Package') {
         steps {
            sh 'mvn clean install -DskipTests -Pproduction'
         }
      }
      stage('Build Docker Image') {
           steps {
              sh 'docker image build -t sethuram975351/gateway:latest .'
           }
      }
      stage('Push to Docker Hub') {
             steps {
                sh 'docker  push sethuram975351/gateway:latest'
             }
      }
//       stage('Kubernetes Deployment') {
//               steps {
//                 sh 'kubectl apply -f gateway-deployment.yaml'
//               }
//       }
   }
}