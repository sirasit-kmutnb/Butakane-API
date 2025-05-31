pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git branch: 'release-1.0.0.0', url: 'https://github.com/sirasit-kmutnb/Butakane-API.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
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
                sh 'kubectl apply -f k8s/deployment.yaml --namespace=butakane-dev'
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
