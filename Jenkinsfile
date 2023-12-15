pipeline {
    agent any
       tools{
        maven 'maven-3.9.6'
       }
    stages {
        stage('Build') {
            steps {
               h 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}