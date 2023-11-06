pipeline {
    agent any
    tools {
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

        stage('Build and Test for the Backend ( adding mvn)') {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }


        /// for front
        stage('Build the Frontend') {
            steps {
                dir('DevOps_Project_Front') {
                    script {
                        sh 'npm install'
                        sh 'ng build '
                    }
                }
            }
        }

       

        stage('Login to my Docker') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }

        stage('Build & Push Docker Image for the Backend Part') {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'docker build -t ibrahimben/devops_project_backend .'
                        sh 'docker push ibrahimben/devops_project_backend'
                    }
                }
            }
        }

        stage('Build & Push Docker Image for the Frontend Part') {
            steps {
                dir('DevOps_Project_Front') {
                    script {
                        sh 'docker build -t ibrahimben/devops_project_frontend .'
                        sh 'docker push ibrahimben/devops_project_frontend'
                    }
                }
            }
        }

        stage('Deploy with Docker Compose the front, back, and MYSQL + graf+prom') {
            steps {
                script {
                    sh 'docker-compose -f docker-compose.yml up -d'
                }
            }
        }

        
        stage('Run Unit Tests with JUNIT') {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'mvn clean test'
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
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

    }

    post {
        success {
            emailext subject: "Success",
                      body: "Success on job  ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL}",
                      to: "ibrahim.benabderrahman@esprit.tn"
        }
        failure {
            emailext subject: "Failure",
                      body: "Failure on job ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL}",
                      to: "ibrahim.benabderrahman@esprit.tn"
    }
}}
