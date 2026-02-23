node('Agent_win_gb') {

stage('checkout'){
checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'ayan', url: 'https://github.com/ayansaiyad/user-activity-processor.git']])
}

stage('test'){
    withSonarQubeEnv {
    bat '''mvn clean verify %SONAR_MAVEN_GOAL% ^
  -Dsonar.projectKey=Maven-CI-Build-Test-SonarQube ^
  -Dsonar.projectName='Maven-CI-Build-Test-SonarQube' ^
  -Dsonar.host.url=%SONAR_HOST_URL% ^
  -Dsonar.token=%SONAR_AUTH_TOKEN% ^
  -Dsonar.qualitygate.wait=true
'''
 }
}
stage('build'){
    bat 'echo "build completed"'
}
}
