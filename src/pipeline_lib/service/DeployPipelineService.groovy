package pipeline_lib.service

class DeployPipelineService {

    static String displayNameForDeployPipeline(String buildNumber, String buildUser) {

        String finalBuildNumber = buildNumber ? buildNumber : '0'
        String finalBuildUser = buildUser ? buildUser : 'Jenkins'

        return "${finalBuildNumber}:${finalBuildUser}"
    }
}
