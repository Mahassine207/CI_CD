pipeline {
    agent any

    environment {
        IMAGE_NAME = "springboot-app"
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Clonage du code'
                checkout scm
            }
        }

        stage('Tests') {
            steps {
                echo 'Exécution des tests'
                sh 'mvn test'
            }
        }

        stage('Build JAR') {
            steps {
                echo 'Build du projet'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Construction de l’image Docker'
                sh 'docker build -t $IMAGE_NAME:latest .'
            }
        }

        stage('Load Image into Minikube') {
            steps {
                echo 'Chargement image dans Minikube'
                sh 'minikube image load $IMAGE_NAME:latest'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Déploiement Kubernetes'
                sh 'kubectl apply -f k8s/'
            }
        }
    }

    post {
        success {
            echo 'Déploiement terminé avec succès'
        }
        failure {
            echo 'Échec du pipeline'
        }
    }
}
