pipeline {
    agent any
    tools{
        jdk 'Java-21' // Update to your version
        maven 'maven-3.9.6' // Update to your version
    }

    stages {
        stage('Build') {
            steps {
               sh 'hello'
            }
        }
        stage('Test') {
            steps {
                sh 'testt'
            }
        }
        stage('SonarQube Analysis'){
            steps{
                withSonarQubeEnv('SonarCube-server') {
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