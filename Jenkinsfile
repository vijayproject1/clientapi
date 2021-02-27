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
                sh "./gradlew build"
            }
        }
        stage('Test') {
            steps {
                sh "./gradlew test"
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploy"
            }
        }
    }
}
