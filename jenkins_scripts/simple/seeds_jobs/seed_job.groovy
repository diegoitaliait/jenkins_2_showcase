package seed_jobs


job("seed-jobs/jenkins-jobs-simple-seed-job") {

    logRotator(30, 30, 30, 1)

    parameters {
        stringParam('seed_job_base_folder', "jenkins-jobs-simple", 'Name of the folder within the jenkins root that will contain all the generated jobs')
        stringParam('seed_git_dsl_scripts_url', "${seedJobInfos.seed_git_dsl_scripts_url}", 'URL git of the repo containing the dsl scripts')
        gitParam('seed_dsl_scripts_branch') {
            description('')
            // Specifies the type of selectable values.
            type('BRANCH')
            branch('')
            // Sets a default value for the parameter.
            defaultValue("${seedJobInfos.seed_git_dsl_scripts_default_branch_beta}")
            // Sets a description for the parameter.
            description('Select the dsl project branch to generate')
            // Specifies the sort order for tags.
            sortMode('ASCENDING')
            // Specifies a filter for tags.
            tagFilter('*')
        }
        stringParam('shared_library_brach', "develop", 'shared library branch')
        choiceParam('script_path_to_generate',
                ['jenkins_scripts/simple/jobs_dsl/**/*.groovy'],
                'Choose the jobs path folder to generate')
    }

    scm {
        git {
            remote {
                url("${seedJobInfos.seed_git_dsl_scripts_url}")
                credentials(JenkinsCredentials.JENKINSCI.id())
            }
            branch('${seed_dsl_scripts_branch}')
            extensions {
                cloneOptions {
                    shallow(true)
                }
            }
        }
    }

    triggers {
        scm('')
    }

    wrappers {
        colorizeOutput()
    }

    steps {
        dsl {
            external '${script_path_to_generate}'
            additionalClasspath('src/main/groovy')
            removeViewAction('IGNORE')
            removeAction('IGNORE')
        }
    }
}
