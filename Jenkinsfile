pipeline {
    agent any
    stages {

        stage('permission') {
                steps {
                        echo "permission"
                        sh 'chmod +x ./gradlew'
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
