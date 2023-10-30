pipeline {
    agent any
    tools{
        nodejs 'NodeJs'
    }

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

        // for backend
        stage('Build and Test Backend') {
            steps {
                dir('DevOps_Project') {
                    script {
                       
                        sh 'mvn clean install -DskipTests' 
                       
                    }
                }
            }

            
        }
        /// for front
        stage('Build Frontend') {
            steps {
                dir('DevOps_Project_Front') {
                    script {
                        
                        sh 'npm install' 
                        sh 'ng build '      
                    }
                }
            }
        }
    
    }


}
