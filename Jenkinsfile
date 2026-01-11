pipeline {
    agent any

    environment {
        MINIKUBE_HOME = "${HOME}/.minikube"
    }

    stages {
        stage('Build JAR') {
            steps {
                echo '📦 Compilation du projet...'
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Start Minikube') {
            steps {
                echo '🚀 Démarrage de Minikube...'
                script {
                    sh '''
                        minikube status || minikube start --driver=docker
                    '''
                }
            }
        }

        stage('Build & Load Image') {
            steps {
                echo '🐳 Construction de l’image Docker...'
                script {
                    sh '''
                        eval $(minikube docker-env)
                        docker build -t springboot-app:latest -f Dockerfile .
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo '🚀 Déploiement sur Minikube...'
                sh '''
                    kubectl apply -f k8s/
                    kubectl rollout status deployment/tp-spring-boot-deployment || echo "Check pods manually"
                    kubectl get pods,svc
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline CI/CD exécuté avec succès !'
        }
        failure {
            echo '❌ Pipeline échoué. Vérifiez que Minikube et Docker sont bien démarrés.'
        }
    }
}
