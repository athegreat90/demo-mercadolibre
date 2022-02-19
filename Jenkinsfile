pipeline
{
    agent
    {
        docker
        {
            image 'maven:3.8.1-adoptopenjdk-11'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    environment
    {
        GIT_COMMIT_SHORT = sh(script: "printf \$(git rev-parse --short ${GIT_COMMIT})", returnStdout: true)
    }
    options
    {
        skipStagesAfterUnstable()
    }
    stages
    {
        stage('Build')
        {
            steps
            {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test')
        {
            steps
            {
                sh 'mvn test'
            }
            post
            {
                always
                {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Sonarqube')
        {
            environment
            {
                scannerHome = tool 'Sonar-scanner'
            }
            steps
            {
                withSonarQubeEnv(credentialsId: 'sonar-credentialsId', installationName: 'Sonar')
                {
                    sh("mvn clean verify sonar:sonar")
//                     sh "${scannerHome}/bin/sonar-scanner -Dsonar.sources=src/ -Dsonar.java.libraries=/var/lib/jenkins/.m2/**/*.jar -Dsonar.java.binaries=target/classes/"
                }
            }
        }
        stage('SQuality Gate')
        {
             steps
             {
                timeout(time: 10, unit: 'MINUTES')
                {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deliver')
        {
            steps
            {
                sh 'mvn clean package -DskipTests && ls -altr'
            }
        }
    }
}
