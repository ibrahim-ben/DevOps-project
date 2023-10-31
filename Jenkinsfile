pipeline {
    agent any
    tools{
        nodejs 'NodeJs'
    }
    environment {
    DOCKERHUB_CREDENTIALS = credentials('ForDevOpsProject')
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

        stage('SonarQube analysis') {
            steps {
                dir('DevOps_Project') {
                    script {
                        withSonarQubeEnv(installationName: 'sonarqube') { 
                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                        }
                    }
                }
            }
        }

        stage('Login Docker') {
            steps {
                sh 'docker login -u $DOCKERHUB_CREDENTIALS_USR --password $DOCKERHUB_CREDENTIALS_PSW'
            }
        }

        stage('Build & Push Docker Image for the Backend Part ') {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'docker build -t ibrahimben/devops_project_backend .'
                        sh 'docker push ibrahimben/devops_project_backend'
                    }
                }
            }
        }

    
    }


}
