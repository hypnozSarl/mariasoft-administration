pipeline {
    agent any
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