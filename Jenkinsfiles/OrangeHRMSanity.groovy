properties([
    parameters([
        string(name: 'Branch', defaultValue: 'main', description: 'Git branch to checkout and build'),
        choice(name: 'ENV', choices: ['QA', 'UAT', 'PROD'], description: 'Application Environment'),
        choice(name: 'browsers', choices: ['chrome', 'firefox', 'edge'], description: 'Target browser for test execution'),
        choice(name: 'suiteXmlFile', 
               choices: [
                   'testsuites/ApprovalFlowsSuite.xml',
                   'testng.xml'
               ], 
               description: 'Select TestNG suite XML to execute'),
        booleanParam(name: 'headless', defaultValue: true, description: 'Run browser in headless mode in CI'),
        booleanParam(name: 'clearWorkspace', defaultValue: false, description: 'Clean workspace before running tests')
    ])
])

node {
    // Resolve Maven from Jenkins Global Tools
    def mvnHome = tool 'Maven-3.9.6'

    stage('Clean Workspace') {
        if (params.clearWorkspace) {
            cleanWs()
        }
    }

    stage('Checkout Code') {
        checkout scm
    }

    stage('Build and Run Tests') {
        int retStatus = 0
        try {
            // Adds Maven and Java to the current PATH
            withEnv(["PATH+MAVEN=${mvnHome}/bin"]) {
                retStatus = sh(
                    script: """
                        mvn clean test \
                        -DsuiteXmlFile="${params.suiteXmlFile}" \
                        -Dbrowser="${params.browsers}" \
                        -Dheadless="${params.headless}" \
                        -Denvironment="${params.ENV}"
                    """,
                    returnStatus: true
                )
            }
        } catch (Exception exp) {
            println "Exception occurred during test execution: ${exp.getMessage()}"
            currentBuild.result = 'UNSTABLE'
        }

        if (retStatus == 1) {
            println "Build failed due to test assertion or execution failure."
            currentBuild.result = 'FAILURE'
        } else if (retStatus > 1) {
            println "Build completed with unstable state."
            currentBuild.result = 'UNSTABLE'
        }
    }

    stage('Generate Allure Report') {
        allure([
            includeProperties: false,
            jdk: '',
            properties: [],
            reportBuildPolicy: 'ALWAYS',
            results: [[path: 'target/allure-results']]
        ])
    }
}