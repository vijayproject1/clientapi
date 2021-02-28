pipeline {
    agent any
    environment {
        registry = "vijayb123/clientapi"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }
    stages {

        stage('permission') {
            steps {
                echo "permission"
                sh 'chmod +x ./gradlew'
            }
        }
        stage('Build') {
            steps {
                sh "./gradlew build"
            }
        }
        
           stage('Docker login') {
            steps {
                sh "docker login vijaydev01reg.azurecr.io -u vijayb123 -p Vijay@123"
                 echo "${WORKSPACE}"

                echo "docker Logged"
            }
        }
        
           stage('Dcoker Image Build') {
            steps {
                sh "docker build -t vijaydev01reg.azurecr.io/clientapi:v1.0 . "
                echo "docker Tagged"
            }
        }
            stage('Docker Image Push') {
            steps {
                sh "docker push vijaydev01reg.azurecr.io/clientapi:v1.0"
                echo "docker Pushed"
            }
        }
        
     
    }
}
