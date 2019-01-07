package jobs_dsl


folder("jenkins_jobs_simple") {}
folder("jenkins_jobs_simple/maven") {}
folder("jenkins_jobs_simple/pipelines") {}

//listView("jenkins2/${folderName}") {
//    description("${folderName} Jobs")
//    filterBuildQueue()
//    filterExecutors()
//    recurse()
//
//    columns {
//        status()
//        weather()
//        name()
//        lastSuccess()
//        lastFailure()
//        lastDuration()
//        buildButton()
//    }
//}

