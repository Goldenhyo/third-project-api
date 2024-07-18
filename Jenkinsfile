pipeline {
    agent any 
    environment {
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
        REACT_TRIGGER_URL = 'http://http://3.36.84.228:8080/job/todomate/build'
        API_TOKEN = 'your-api-token'
        USERNAME = 'your-jenkins-username'
    }
    stages {
        stage('Checkout') {
            steps {
                // Git 저장소에서 소스 코드 체크아웃
                git branch: 'master', url: 'https://github.com/Goldenhyo/todomateApi.git'
            }
        }
        stage('Build & Deploy') {
            steps {
                // Docker Compose를 사용하여 배포
                sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
            }
        }
        stage('Trigger React Build') {
            steps {
                script {
                    sh "curl -X POST ${REACT_TRIGGER_URL}"
                }
            }
        }
    }
    post {
        always {
            // 빌드 후 클린업 작업
            cleanWs()
        }
    }
}
