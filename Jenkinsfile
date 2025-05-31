pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git branch: 'release-1.0.0.0', url: 'https://github.com/artnont/Butakane-API.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x ./mvnw && ./mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'chmod +x ./mvnw && ./mvnw test'
            }
        }

        stage('Deploy') {
            steps {
                sh 'kubectl apply -f k8s/deployment.yaml --namespace=butakane-dev'
            }
        }
    }

    post {
        success {
            echo '✅ Build and deploy successful!'
        }
        failure {
            echo '❌ Build or deploy failed.'
        }
    }
}
