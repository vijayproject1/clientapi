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
                echo "./gradlew build"
            }
        }
        stage('Test') {
            steps {
                echo "./gradlew test"
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploy"
            }
        }
    }
}
