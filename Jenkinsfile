pipeline {
    agent any

    tools {
        maven 'Maven-3'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Clonage du projet depuis GitHub'
            }
        }

        stage('Build') {
            steps {
                echo 'Compilation'
                sh 'mvn clean compile'
            }
        }

        stage('Tests') {
            steps {
                echo 'Tests unitaires'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging'
                sh 'mvn package'
            }
        }
    }
}
