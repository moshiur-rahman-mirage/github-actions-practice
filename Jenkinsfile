pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn --batch-mode clean verify'
                    } else {
                        bat 'mvn --batch-mode clean verify'
                    }
                }
            }
        }

        stage('Archive application') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
