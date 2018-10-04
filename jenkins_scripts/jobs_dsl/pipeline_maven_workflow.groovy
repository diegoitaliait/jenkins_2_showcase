import static com.igt.interactive.jenkins.environment.EJobEnvironmentVariableNames.*

pipelineJob("jenkins2/pipelines/pipeline_maven_workflow") {

    logRotator(30, 30, 30, 1)
    wrappers {
        colorizeOutput()
        timestamps()
    }


    parameters {
        stringParam('git_branch', 'master', 'Git branch')
        stringParam('git_url', 'https://github.com/diegoitaliait/gs-spring-boot.git', 'Git URL')
    }
    environmentVariables {
        env(MAVEN_VERSION, 'maven_354')
    }

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url("https://github.com/diegoitaliait/jenkins_2_showcase.git")
//            credentials(Projects.jenkinsGitCiIgamingCredentialsId)
                    }
                    branch("master")
                    extensions {
                        cloneOptions {
                            shallow(true)
                        }
                    }
                }
            }
            scriptPath("jenkins_scripts/pipelines_dsl/_maven_workflow.groovy")
        }
    }
}
