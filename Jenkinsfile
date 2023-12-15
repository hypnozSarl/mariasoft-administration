pipeline {
    agent any
    tools{
        jdk 'Java21' // Update to your version
        maven 'maven-3.9.6' // Update to your version
    }

    stages {
        stage('Cleanup Workspace'){
            steps{
                deleteDir()
            }
        }
        stage('Build') {
            steps {
               sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('SonarQube Analysis'){
            steps{
                withSonarQubeEnv('My SonarQube Server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}