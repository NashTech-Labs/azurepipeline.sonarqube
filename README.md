# ADO Pipeline Template for Docker Build

This template contains an Azure pipeline that can be extended to use sonarqube task to have static ci testng of maven source code under different Azure pipelines.

## use case

You need to have a service connection related to GitHub on the Azure DevOps project. And also service connection for sonrqube application.
To call this in your pipeline you can follow this example:

   ```yaml

# azure-pipeline.yml
  resources:
    repositories:
      - repository: Docker
        type: github
        name: NashTech-Labs/ado.docker.push
        ref: main
        endpoint: '<GitHub Service Connection>'

  steps:

  - task: Maven@4
  inputs:
    mavenPomFile: 'pom.xml'
    goals: 'clean test'
    publishJUnitResults: true
    testResultsFiles: '**/surefire-reports/TEST-*.xml'
    testRunTitle: 'TestRUNtitle'
    codeCoverageToolOption: 'JaCoCo'
    codeCoverageClassFilesDirectories: 'target/classes,target/test-classes'
    codeCoverageSourceDirectories: 'src/main,src/test'
    javaHomeOption: 'JDKVersion'
    mavenVersionOption: 'Default'
    mavenAuthenticateFeed: false
    effectivePomSkip: false
    sonarQubeRunAnalysis: false


  - template: sonarmaven.yml@Docker
    parameters:
        SonarQube: 'SonarQubeConnection'
        mavenPomFile: 'pom.xml'
        extraProperties: |
            # Project identification
            sonar.projectKey=Example_AYmZC4QfQS0Ebj7IMMdk
            sonar.projectName=Example
            sonar.projectVersion=2.0

            # Source code directories
            sonar.sources=src/main/java

            # Test directories
            sonar.tests=src/test/java

            # Language
            sonar.language=java

            # Encoding
            sonar.sourceEncoding=UTF-8

            # Additional properties
            sonar.java.binaries=target/classes
            # sonar.java.libraries=target/lib/**/*.jar
            sonar.java.test.binaries=target/test-classes
            # sonar.java.test.libraries=target/lib/**/*.jar
            sonar.exclusions=**/*.class
            sonar.junit.reportPaths=**/surefire-reports
            sonar.coverage.jacoco.xmlReportPaths=**/surefire-reports/TEST-*.xml

  ```

You can also use Variable Group and Azure Key Vault for values.
