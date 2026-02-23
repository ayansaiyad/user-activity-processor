pipeline {
    agent { label 'Agent_win_gb' }

    tools {
        maven 'M3'
    }

    environment {
        // Isse aapka credential secure (hide) rahega
        SONAR_TOKEN = credentials('Sonar-qube')
    }

    stages {
        stage('Checkout') {
            steps {
                    checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'ayan', url: 'https://github.com/ayansaiyad/user-activity-processor.git']])
                )
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube-Server') {
                    // Windows (bat) mein credential ko % se call karte hain hide rakhne ke liye
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
