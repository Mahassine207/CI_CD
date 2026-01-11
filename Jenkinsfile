pipeline {
    agent any

    stages {
        stage('Build JAR') {
            steps {
                echo '📦 Compilation du projet...'
                sh 'chmod +x mvnw' 
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build & Load Image (Minikube)') {
            steps {
                echo '🐳 Construction de l’image directement dans Minikube...'
                script {
                    // On force l'utilisation du Docker interne de Minikube
                    // Cela évite l'erreur "docker not found" si Docker est installé via Minikube
                    sh '''
                        eval $(minikube docker-env)
                        docker build -t springboot-app:latest .
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo '🚀 Déploiement sur Minikube...'
                // On s'assure que kubectl pointe sur le bon cluster
                sh 'kubectl apply -f k8s/'
                
                echo '⏳ Attente du déploiement...'
                sh 'kubectl rollout status deployment/tp-spring-boot-deployment || echo "Check pods manually"'
                
                echo '📊 État des ressources :'
                sh 'kubectl get pods,svc'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline CI/CD exécuté avec succès !'
        }
        failure {
            echo '❌ Pipeline échoué. Vérifiez si Minikube est bien démarré.'
        }
    }
}