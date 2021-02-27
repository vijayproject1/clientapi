pipeline {
    agent any
    stages {
     stage('clean') {
            steps {
                    echo "clean"
                    sh 'chmod +x ./gradlew'
                    sh './gradlew clean'
                }
            }
        stage('Build') {
            steps {
                echo "build"
            }
        }
        stage('Test') {
            steps {
                echo "Test"
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploy"
            }
        }
    }
}