import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class OfflineBuildPart2 extends DefaultTask {

    @TaskAction
    public void run(){

        getProject().setBuildDir("D:\\IDPr\\Practice\\build");
        System.out.println("Projetct dir set to build !");

    }

}
