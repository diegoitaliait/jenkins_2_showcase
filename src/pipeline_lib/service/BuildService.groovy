package pipeline_lib.service

class BuildService {

  static String displayName() {

    "Sono una shared library"
  }

  static String forceUnstableOnFailure(currentBuild, String buildResult) {

    if (buildResult == 'FAILURE') {
      currentBuild.result = 'UNSTABLE' // of FAILURE
      return 'UNSTABLE'
    }
  }
}
