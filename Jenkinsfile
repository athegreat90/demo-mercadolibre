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
                withSonarQubeEnv('SonarQubeScanner')
                {
                    sh("mvn clean verify sonar:sonar")
                }
//                 withCredentials([string(credentialsId: 'sonar-credentialsId', variable: 'SONAR_TOKEN')])
//                 {
//                     sh("mvn clean verify sonar:sonar -Dsonar.login=${SONAR_TOKEN} -Dsonar.host.url=http://192.168.1.217:9000/")
//                 }
//                 withSonarQubeEnv(credentialsId: 'sonar-credentialsId', installationName: 'Sonar')
//                 {
//                     sh("mvn clean verify sonar:sonar")
// //                     sh "${scannerHome}/bin/sonar-scanner -Dsonar.sources=src/ -Dsonar.java.libraries=/var/lib/jenkins/.m2/**/*.jar -Dsonar.java.binaries=target/classes/"
//                 }
            }
        }
        stage('SQuality Gate')
        {
            steps
            {
                waitForQualityGate abortPipeline: true

//                 script
//                 {

//                     timeout(time: 2, unit: 'MINUTES')
//                     {
//                         def qg = waitForQualityGate()
//                         if (qg.status == 'ERROR')
//                         {
//                             error "Pipeline aborted due to quality gate failure: ${qg.status}"
//                         }
//                         echo "Pipeline quality gate: ${qg.status}"
//                     }
//                 }
            }
//              steps
//              {
//                 timeout(time: 2, unit: 'MINUTES')
//                 {
//                     waitForQualityGate abortPipeline: true
//                 }
//             }

        }
        stage('Deliver')
        {
            steps
            {
                sh 'mvn clean package docker:build -DskipTests'
            }
        }
        stage('Clean')
        {
            steps
            {
                sh 'docker system prune -a -f'
                sh 'mvn clean'
            }
        }
    }
}
