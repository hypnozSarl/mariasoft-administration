pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/hypnozSarl/mariasoft-administration.git'
            }
        }
        stage('Build with Maven') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}