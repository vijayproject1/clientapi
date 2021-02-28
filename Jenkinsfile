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
                sh "docker login -u vijayb123 -p Vijay@123"
                echo "docker Tagged"
            }
        }
        
            stage('Docker Push') {
            steps {
                sh "docker tag clientapi vijaydev01reg.azurecr.io/clientapi:v1.0"
                sh "docker push vijaydev01reg.azurecr.io/clientapi"
                echo "docker Pushed"
            }
        }
        
     
    }
}
