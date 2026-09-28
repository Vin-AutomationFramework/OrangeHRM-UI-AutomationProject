import groovy.json.JsonSlurper

// Define pipeline execution properties and UI parameters matching your enterprise setup
properties([
    parameters([
        string(name: 'Branch', defaultValue: 'main', description: 'Git branch to checkout and build'),[cite: 16]
        choice(name: 'ENV', choices: ['QA', 'UAT', 'PROD'], description: 'Application Environment'),[cite: 16]
        choice(name: 'browsers', choices: ['chrome', 'firefox', 'edge'], description: 'Target browser for test execution'),[cite: 16]
        choice(name: 'suiteXmlFile', 
               choices: [
                   
                   'testng.xml'
               ], 
               description: 'Select TestNG suite XML to execute'),[cite: 15, 16]
        booleanParam(name: 'headless', defaultValue: true, description: 'Run browser in headless mode in CI'),
        booleanParam(name: 'clearWorkspace', defaultValue: false, description: 'Clean workspace before running tests')[cite: 16]
    ])
])

// Use an available Jenkins node (replace 'swarm-mac' with 'any' or your agent label)
node('any') {[cite: 15, 16]

    stage('Clean Workspace') {
        if (params.clearWorkspace) {
            cleanWs()
        }
    }

    stage('Checkout Code') {
        // Checks out the selected Git branch from your repository
        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${params.Branch}"]],
            userRemoteConfigs: scm.userRemoteConfigs
        ])
    }

    stage('Build and Run Tests') {[cite: 15]
        int retStatus = 0
        try {
            // Uses configured JDK and Maven tool bindings
            withMaven(jdk: 'JDK-17', maven: 'Maven-3.9.6') {[cite: 15]
                // Executes clean test overriding XML, browser, headless, and environment via Maven properties
                retStatus = sh(
                    script: """
                        mvn clean test \
                        -DsuiteXmlFile="${params.suiteXmlFile}" \
                        -Dbrowser="${params.browsers}" \
                        -Dheadless="${params.headless}" \
                        -Denvironment="${params.ENV}"
                    """,[cite: 15]
                    returnStatus: true[cite: 15]
                )
            }
        } catch (Exception exp) {
            println "Exception occurred during test execution: ${exp.getMessage()}"[cite: 15]
            currentBuild.result = 'UNSTABLE'[cite: 15]
        }

        // Test status evaluation matching your enterprise build logic
        if (retStatus == 1) {[cite: 15]
            println "Build failed due to test assertion or execution failure."
            currentBuild.result = 'FAILURE'[cite: 15]
        } else if (retStatus > 1) {[cite: 15]
            println "Build completed with unstable state."
            currentBuild.result = 'UNSTABLE'[cite: 15]
        }
    }

    stage('Generate Allure Report') {[cite: 15]
        // Publishes the Allure HTML report inside Jenkins
        allure([
            includeProperties: false,[cite: 15]
            jdk: '',[cite: 15]
            properties: [],[cite: 15]
            reportBuildPolicy: 'ALWAYS',[cite: 15]
            results: [[path: 'target/allure-results']][cite: 15]
        ])
    }
}