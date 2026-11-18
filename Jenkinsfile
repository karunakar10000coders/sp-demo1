
pipeline {
    agent any

    environment {
        IMAGE_NAME = 'karunakar0212/java-sp-image'
        IMAGE_TAG = 'latest'
        DEPLOY_HOST = '3.110.189.17'
        DEPLOY_USER = 'ubuntu'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/karunakar10000coders/sp-demo1.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Build and Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login \
                            -u "$DOCKER_USER" --password-stdin

                        docker build \
                            -t $IMAGE_NAME:$IMAGE_TAG .

                        docker push $IMAGE_NAME:$IMAGE_TAG

                        docker logout
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sshagent(credentials: ['deploy-ssh-key']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=accept-new \
                            $DEPLOY_USER@$DEPLOY_HOST "
                                docker pull $IMAGE_NAME:$IMAGE_TAG &&
                                (docker rm -f springboot-crud || true) &&
                                docker run -d \
                                    --name springboot-crud \
                                    --restart unless-stopped \
                                    -p 8080:8080 \
                                    $IMAGE_NAME:$IMAGE_TAG
                            "
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Application deployed successfully!'
        }

        failure {
            echo 'Deployment failed. Check Jenkins console logs.'
        }
    }
}

