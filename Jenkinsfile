pipeline {
    agent any
    tools { 
        maven 'maven-3.8.6' 
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
       
        stage('SonarQube Analysis'){
            steps{
                withSonarQubeEnv() {
                        sh 'mvn clean verify sonar:sonar ^
                        -Dsonar.projectKey=Maven-Jenkinsfile ^
                        -Dsonar.host.url=%SONAR_HOST_URL% ^
                        -Dsonar.login=%SONAR_AUTH_TOKEN%
                }
            }
        }
        stage("Quality Gate") {
            steps {
              timeout(time: 1, unit: 'HOURS') {
                waitForQualityGate abortPipeline: true
              }
            }
        }
   
}

    
