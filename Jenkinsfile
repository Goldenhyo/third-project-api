pipeline {
    agent any 
    environment {
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
        REACT_TRIGGER_URL = 'http://3.36.84.228:8080/job/todomate/build'
        API_TOKEN = 'todomate'  // 생성한 API 토큰
        USERNAME = 'jeha'
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
                    sh "curl -X POST ${REACT_TRIGGER_URL} --user ${USERNAME}:${API_TOKEN}"
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