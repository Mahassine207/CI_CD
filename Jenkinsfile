pipeline {
    agent any

    environment {
        IMAGE_NAME = 'mahassine180/springboot-app:latest'
    }

    stages {
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
            echo '✅ Déploiement terminé !'
        }
        failure {
            echo '❌ Échec du déploiement.'
        }
    }
}
