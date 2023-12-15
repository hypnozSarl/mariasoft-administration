pipeline {
    agent any
    tools{
        maven 'maven3'
    }
    stages {
        stage('Build with Maven') {
            steps {
               sh 'mvn clean'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}