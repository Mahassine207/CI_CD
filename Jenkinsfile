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
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '🐳 Construction de l’image Docker...'
                sh "docker build -t ${IMAGE_NAME} -f Dockerfile ."
            }
        }

        stage('Push Docker Image') {
            steps {
                echo '⬆️ Push sur Docker Hub...'
                withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                    sh """
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push ${IMAGE_NAME}
                    """
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo '🚀 Déploiement sur Minikube via Docker Hub...'
                sh """
                    kubectl set image deployment/tp-spring-boot-deployment springboot-app=${IMAGE_NAME}
                    kubectl rollout status deployment/tp-spring-boot-deployment
                    kubectl get pods,svc
                """
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline CI/CD exécuté avec succès !'
        }
        failure {
            echo '❌ Pipeline échoué. Vérifie Jenkins, Docker Hub et Minikube.'
        }
    }
}
