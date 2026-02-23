pipeline {
    agent { label 'Agent_win_gb' }

    tools {
        maven 'M3'
    }

    environment {
        SONAR_TOKEN = credentials('sonartoken')
    }

    stages {
        stage('Checkout Source') {
            steps {
                // Aapka bataya hua scmGit block yahan set kar diya hai
                checkout scmGit(
                    branches: [[name: '*/main']], 
                    extensions: [], 
                    userRemoteConfigs: [[credentialsId: 'ayan', url: 'https://github.com/ayansaiyad/user-activity-processor.git']]
                )
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube-Server') {
                    bat "mvn clean verify sonar:sonar -Dsonar.projectKey=Maven-Jenkinsfile -Dsonar.token=%SONAR_TOKEN%"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
    }
}
