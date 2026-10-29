pipeline {
    agent any

    tools {
        jdk 'jdk17'                       
    }

    environment {
        STUDENT_NAME = 'Kali Aishah'
        STUDENT_ID   = '37830'
        IMAGE_NAME   = 'kali-aishah-student-api'
        IMAGE_TAG    = 'v1.1-kali-aishah-37830'
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '15'))
    }

    stages {
        stage('Checkout') {
            steps {
                echo "=== Checkout | Student: ${STUDENT_NAME} (ID ${STUDENT_ID}) ==="
                checkout scm
                sh 'git log -1 --oneline'
            }
        }

        stage('Build') {
            steps {
                echo "=== Build | ${STUDENT_NAME}: compiling Spring Boot app with Gradle ==="
                sh 'chmod +x gradlew'
                sh './gradlew clean bootJar -x test --no-daemon'
            }
        }

        stage('Test') {
            steps {
                echo "=== Test | ${STUDENT_NAME}: running automated tests ==="
                sh './gradlew test --no-daemon'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo "=== Docker Build | ${STUDENT_NAME}: ${IMAGE_NAME}:${IMAGE_TAG} ==="
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Verify') {
            steps {
                echo "=== Verify | ${STUDENT_NAME}: checking that the image exists ==="
                sh "docker image inspect ${IMAGE_NAME}:${IMAGE_TAG} --format 'Image: {{index .RepoTags 0}} | Size: {{.Size}} bytes | Created: {{.Created}}'"
                sh "docker images ${IMAGE_NAME}"
            }
        }
    }

    post {
        success {
            echo "SUCCESS: pipeline of ${STUDENT_NAME} finished, image ${IMAGE_NAME}:${IMAGE_TAG} is ready"
        }
        failure {
            echo "FAILURE: pipeline of ${STUDENT_NAME} failed — open the red stage and read its log"
        }
    }
}

