mavenJob("maven/maven_vanilla_job") {
  logRotator(30, 30, 30, 1)
//  jdk('JDK')

  wrappers {
    colorizeOutput()
    timestamps()
  }

  parameters {
    stringParam('branch', 'master', 'Project branch')
    stringParam('email_report', '', 'List of people to get the report for the builds')
  }

  scm {
    git {
      remote {
        url('https://github.com/diegoitaliait/gs-spring-boot.git')
//        credentials('ssh-private-key')
      }
      branch('${branch}')
      extensions {
        cloneOptions {
          shallow(true)
        }
      }
    }
  }

  mavenInstallation("maven_354")
  rootPOM('./complete/pom.xml')

  mavenOpts('-Xmx1024m ')
  mavenOpts('-XX:MaxPermSize=1024m')
  mavenOpts('-client -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Xverify:none')
  mavenOpts('-Dmaven.javadoc.skip=true -Dmaven.artifact.threads=12 -Dhttp.tcp.nodelay=false -Dmaven.wagon.provider.http=httpclient')

  goals('clean install')
  goals('-T 1C')

  //noinspection GroovyAssignabilityCheck
  properties {
    copyArtifactPermissionProperty {
      projectNames('*')
    }
  }

  publishers {

    archiveArtifacts {
      pattern('*.xml')
      onlyIfSuccessful()
      allowEmpty(false)
      defaultExcludes(true)
    }

    extendedEmail {
      recipientList('${email_report}')
      contentType('text/html')
      triggers {
        always {
          attachBuildLog(true)
          subject('$DEFAULT_SUBJECT')
          content('$DEFAULT_CONTENT')
          contentType('text/html')
        }
      }
    }
  }
}
