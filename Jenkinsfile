pipeline {
    agent any
    tools { 
        maven 'maven-3.8.6' 
    }
    stages {
        stage('Checkout git') {
            steps {
               git branch: 'main', url: 'https://github.com/ayansaiyad/user-activity-processor.git'
            }
        }
        
       
        stage('SonarQube Analysis'){
            steps{
                withSonarQubeEnv() {
                        sh 'mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=Maven-Jenkinsfile \
                        -Dsonar.host.url=%SONAR_HOST_URL% \
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

    
