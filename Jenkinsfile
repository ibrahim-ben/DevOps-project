pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                checkout scm
            }
        }

        stage('Run Unit Tests JUNIT') {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'mvn clean test' 
                    }
                }
            }
        }

         stage('Build and Test Backend') {
            steps {
                dir('DevOps_Project') {
                    script {
                       
                        sh 'mvn clean install -DskipTests' 
                       
                    }
                }
            }

            
        }

    
    }


}
