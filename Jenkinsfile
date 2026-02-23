pipeline {
    // Specific Windows Agent define kiya hai
    agent { label 'Agent_win_gb' }

    tools {
        // Ensure 'M3' is configured in Jenkins Global Tool Configuration
        maven 'M3'
    }

    environment {
        // Credentials handle karne ka sahi tarika
        SONAR_AUTH_TOKEN = credentials('ayan') 
        SCANNER_HOME = tool 'M3'
    }

    stages {
        stage('Checkout') {
            steps {
                // Aapka real repo URL aur credentials
                checkout scmGit(
                    branches: [[name: '*/main']], 
                    userRemoteConfigs: [[credentialsId: 'ayan', url: 'https://github.com']]
                )
            }
        }

        stage('Build & Test') {
            steps {
                // Windows pe 'bat' use hota hai
                bat 'mvn clean verify -DskipTests=false'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // 'SonarQube-Server' woh naam hai jo Jenkins System Config mein save hai
                withSonarQubeEnv('SonarQube-Server') {
                    bat """
                    mvn sonar:sonar ^
                    -Dsonar.projectKey=Maven-CI-Build-Test-SonarQube ^
                    -Dsonar.projectName=Maven-CI-Build-Test-SonarQube ^
                    -Dsonar.token=%SONAR_AUTH_TOKEN%
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                // Professional pipelines hamesha result ka wait karti hain
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy/Artifact') {
            steps {
                echo "Build successfully verified by SonarQube!"
                // Aap yahan artifacts save kar sakte hain
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
    }
    
    post {
        failure {
            echo "Pipeline failed! Check SonarQube or Unit Tests."
        }
    }
}
