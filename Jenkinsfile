pipeline {
    agent any
    stages {
        stage("list branches") {
            steps {
                script {
                    branches = sh(script: "git branch -r", returnStdout: true).trim()
                    echo "${branches}"
                }
            }
        }
        stage('Build with Maven') {
            steps {
                withMaven {
                         sh "mvn clean verify"
                       }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}