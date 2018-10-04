pipeline {
    agent any

    stages {
        stage('Configuration') {
            steps {
                script {
                    gitBranch = "${git_branch}"
                    gitUrl = "${git_url}"
                    mavenVersion = "${env.MAVEN_VERSION}"
                    pipelineAbsolutePath = pwd()

//                    currentBuild.displayName =
//                            DeployPipelineService.displayNameForDeployPipeline(env.BUILD_DISPLAY_NAME,
//                                    env.BUILD_USER)
                }
            }
        }
        stage('Download resources') {
            //noinspection GroovyAssignabilityCheck
            parallel {
                stage('Download code') {
                    steps {
                        echo '<Download code>'
                        checkout(
                                [$class                           : 'GitSCM',
                                 branches                         : [[name: "${gitBranch}"]],
                                 doGenerateSubmoduleConfigurations: false,
                                 extensions                       : [[$class: 'RelativeTargetDirectory', relativeTargetDir: "."]],
                                 submoduleCfg                     : [],
                                 userRemoteConfigs                : [[url: "${gitUrl}"]]])

                        echo '</ Download code>'
                    }
                }
                stage('Hello') {
                    steps {
                        echo '</ Hello>'
                    }
                }
            }
        }
        stage('Maven clean') {
            steps {
                echo '<clean>'
                script {

                    withMaven(
                            maven: "${mavenVersion}",
//                        jdk: "${pipe_jdk}",
//                        mavenSettingsFilePath: "${maven_settings}",
                            mavenOpts: "-client -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Xverify:none") {
                        dir("complete") {
                            sh "export IE_MAVEN_AGENT_DISABLED=true"
                            sh "mvn clean"
                        }
                    }
                }


                echo '</ clean>'
            }
        }
        stage('Maven test') {
            steps {
                echo '<test>'

                script {

                    withMaven(
                            maven: "${mavenVersion}",
//                        jdk: "${pipe_jdk}",
//                        mavenSettingsFilePath: "${maven_settings}",
                            mavenOpts: "-client -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Xverify:none") {

                        dir("complete") {
                            sh "export IE_MAVEN_AGENT_DISABLED=true"
                            sh "mvn test"
                        }

                    }
                }


                echo '</ test>'
            }
        }
        stage('Maven package') {
            steps {
                echo '<package>'

                script {

                    withMaven(
                            maven: "${mavenVersion}",
//                        jdk: "${pipe_jdk}",
//                        mavenSettingsFilePath: "${maven_settings}",
                            mavenOpts: "-client -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Xverify:none") {
                        dir("complete") {
                            sh "export IE_MAVEN_AGENT_DISABLED=true"
                            sh "mvn package"
                        }

                    }
                }

                echo '</ package>'
            }
        }
    }
}
