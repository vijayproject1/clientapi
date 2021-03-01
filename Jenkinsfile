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
                sh "./gradlew clean build"
            }
        }      
           stage('Dcoker Deploy') {
            steps {
                sh "docker login vijaydev01reg.azurecr.io -u vijaydev01reg -p 5gohA116a0W/Jx5zb7hVMrt=FsOfg5+R"
                sh "docker build -t vijaydev01reg.azurecr.io/clientapi:latest . "
                sh "docker push vijaydev01reg.azurecr.io/clientapi:latest"
                echo "docker Pushed successfullt"
            }
        }

    }
}
