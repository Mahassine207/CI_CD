pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = 'docker-hub-credentials'   
        IMAGE_NAME = 'mahassine180/springboot-app:latest'   
    }

    stages {
        stage('Build JAR') {
            steps {
                echo '📦 Compilation du projet...'
                powershell 'chmod +x mvnw'
                powershell './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '🐳 Construction de l’image Docker...'
                powershell "docker build -t ${IMAGE_NAME} -f Dockerfile ."
            }
        }

        stage('Push Docker Image') {
            steps {
                echo '⬆️ Push sur Docker Hub...'
                withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                    powershell """
                        echo $env:DOCKER_PASS | docker login -u $env:DOCKER_USER --password-stdin
                        docker push ${IMAGE_NAME}
                    """
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo '🚀 Déploiement sur Minikube via Docker Hub...'
                powershell """
                    kubectl set image deployment/tp-spring-boot-deployment springboot-app=${IMAGE_NAME}
                    kubectl rollout status deployment/tp-spring-boot-deployment
                    kubectl get pods,svc
                """
            }
        }
    }

    post {
        success {
            echo 'Pipeline CI/CD exécuté avec succès ! Application mise à jour sur Minikube.'
        }
        failure {
            echo 'Pipeline échoué. Vérifie Jenkins, Docker Hub et Minikube.'
        }
    }
}
