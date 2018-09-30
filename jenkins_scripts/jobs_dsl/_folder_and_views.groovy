package jobs_dsl

import main.groovy.jenkins.showcase.Folders

Folders.loadRootFolders().each { folderName ->
    folder("${folderName}") {}

    listView("${folderName}/${folderName}") {
        description("${folderName} Jobs")
        filterBuildQueue()
        filterExecutors()
        recurse()

        columns {
            status()
            weather()
            name()
            lastSuccess()
            lastFailure()
            lastDuration()
            buildButton()
        }
    }
}