node {
  def image
   //1//  
   stage ('checkout') {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: 'https://github.com/haivutuan93/icommerce.git']]])
    }
   
    //2//
    stage ('Build') {
        def mvnHome = tool name: 'maven', type: 'maven'
        def mvnCMD = "${mvnHome}/bin/mvn"
        sh "${mvnCMD} clean package"
    }
       
       
   //3// 
    stage ('Docker Build') {
        docker.build('icommerce')
    }

    //4//
    stage ('Docker push')
        docker.withRegistry('public.ecr.aws/l6u8w3k1/icommerce', 'ecr:us-east-1:121152775641') {
        docker.image('icommerce').push('latest')
    }


}