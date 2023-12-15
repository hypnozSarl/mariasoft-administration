pipeline {
    agent any
       tools{
        maven 'maven-3.9.6'
       }
    environment {
       SONAR_TOKEN = credentials('sqa_4ee0d0098e9c881d14a61b040b524634568bc05e')
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
        stage('SonarQube Analysis'){
            steps{
                sh 'mvn sonar:sonar'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}