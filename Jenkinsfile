pipeline {
    agent any

    environment {
        IMAGE_NAME = "lysist/butakane-api"
        IMAGE_TAG = "alpha-${BUILD_NUMBER}"
        REGISTRY_CREDENTIALS = "dockerhub"
        HELM_CHART_PATH = "./k8s/helm/butakane-api"
        VALUES_FILE = "${HELM_CHART_PATH}/values-alpha.yaml"
        NAMESPACE = "butakane-dev"
    }

    stages {
        stage('Clone') {
            steps {
                git branch: 'release-1.0.0.0', url: 'https://github.com/artnont/Butakane-API.git'
            }
        }

        stage('Build JAR') {
            steps {
                sh './mvnw clean package -DskipTests -Dspring.profiles.active=test'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh './mvnw test -Dspring.profiles.active=test'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: "$REGISTRY_CREDENTIALS", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh """
                        docker build -t $IMAGE_NAME:$IMAGE_TAG .
                        echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin
                        docker push $IMAGE_NAME:$IMAGE_TAG
                    """
                }
            }
        }

        stage('Deploy to Alpha with Helm') {
            steps {
                withEnv(["KUBECONFIG=/var/lib/jenkins/.kube/config"]) {
                    sh """
                        helm upgrade --install butakane-api-alpha $HELM_CHART_PATH \
                          -f $VALUES_FILE \
                          --namespace $NAMESPACE \
                          --set image.tag=$IMAGE_TAG
                    """
                }
            }
        }
    }

    post {
        success {
            writeFile file: 'version.txt', text: "${IMAGE_TAG}"
            archiveArtifacts artifacts: 'version.txt'
            echo "✅ Deployed to alpha: $IMAGE_NAME:$IMAGE_TAG"
        }
        failure {
            echo "❌ Alpha deployment failed"
        }
    }
}
