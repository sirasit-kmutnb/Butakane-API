pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/sirasit-kmutnb/Butakane-API/tree/release-1.0.0.0'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Deploy') {
            steps {
                // Example: Using kubectl to deploy
                sh 'kubectl apply -f k8s/deployment.yaml'
            }
        }
    }

    post {
        success {
            echo 'Build and deploy successful!'
        }
        failure {
            echo 'Build or deploy failed.'
        }
    }
}
