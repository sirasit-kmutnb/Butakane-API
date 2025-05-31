pipeline {
    agent any

    environment {
        IMAGE_NAME = "lysist/butakane-api"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        REGISTRY_CREDENTIALS = "dockerhub"
    }

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

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "$REGISTRY_CREDENTIALS", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh '''
                        echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin
                        docker push $IMAGE_NAME:$IMAGE_TAG
                    '''
                }
            }
        }

        stage('Deploy to K8s') {
            steps {
                withEnv(["KUBECONFIG=/var/lib/jenkins/.kube/config"]) {
                    sh '''
                        kubectl set image deployment/butakane-api \
                          butakane-api=$IMAGE_NAME:$IMAGE_TAG \
                          -n butakane-dev
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployed: $IMAGE_NAME:$IMAGE_TAG"
        }
        failure {
            echo "❌ Build failed!"
        }
    }
}

