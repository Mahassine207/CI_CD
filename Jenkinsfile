pipeline {
    agent any

    tools {
        maven 'Maven-3'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Récupération du code source...'
            }
        }

        stage('Build JAR') {
            steps {
                echo 'Compilation et création du JAR...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Construction de l\'image Docker...'
                sh 'docker build -t springboot-app .'
                
                echo 'Chargement de l\'image dans Minikube...'
                sh 'minikube image load springboot-app'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Déploiement sur le cluster Minikube...'
                sh 'kubectl apply -f k8s/deployment.yaml'
                sh 'kubectl apply -f k8s/service.yaml'
                
                echo 'Vérification du déploiement...'
                sh 'kubectl get pods'
            }
        }
    }

    post {
        success {
            echo 'Le pipeline a réussi !'
        }
        failure {
            echo 'Le pipeline a échoué. Vérifiez les logs ci-dessus.'
        }
    }
}