import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class OfflineBuildPart1 extends DefaultTask {

    @TaskAction
    public void run(){

        getProject().setBuildDir("D:\\Spigot\\1.8.0 Dev offline - PvpBox");
        System.out.println("Projetct dir set to plugins !");

    }

}
