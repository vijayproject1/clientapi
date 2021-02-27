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
        stage('Test') {
            steps {
                sh "./gradlew test"
            }
        }
        
        stage('Building our image') { 
            steps { 
                script { 
                    dockerImage = docker.build registry + "SNAP01" 
                }
            } 
        }
        stage('Deploy our image') { 
            steps { 
                script { 
                    docker.withRegistry( '', registryCredential ) { 
                        dockerImage.push() 
                    }
                } 
            }
        } 
        stage('Deploy') {
            steps {
                echo "Deploy"
            }
        }
    }
}
