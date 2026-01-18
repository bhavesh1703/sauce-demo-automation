pipeline {
    agent any

    parameters {
        choice(
            name:'SUITE',
            choices:['smoke','regression'],
            description:'Select which TestNG suite to run'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Test Suite') {
            steps {
                echo "Running suite: ${params.SUITE}"
                bat "mvn clean test -DsuiteXmlFile=src/test/resources/suites/${params.SUITE}.xml"
            }
        }
    }
}