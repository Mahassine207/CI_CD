pipeline {
    agent any

    stages {
        stage('Build JAR') {
            steps {
                echo 'Correction des permissions et compilation...'
                // Donne les droits d'exécution au wrapper Maven
                sh 'chmod +x mvnw' 
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Image') {
            steps {
                sh 'docker build -t springboot-app:latest .'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Construction de l’image Docker...'
                sh 'docker build -t springboot-app:latest .'

                echo 'Chargement de l’image dans Minikube...'
                sh 'minikube image load springboot-app:latest'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Déploiement sur Minikube...'
                sh 'kubectl apply -f k8s/'

                echo 'État des pods'
                sh 'kubectl get pods'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline CI/CD exécuté avec succès !'
        }
        failure {
            echo '❌ Pipeline échoué. Vérifie les logs.'
        }
    }
}