import com.lc.Executor;
import com.lc.Params;
import com.lc.Result;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 10:44 AM
 */
public class SandBoxTest {
    public static void main(String[] args) {
        Params params = new Params();

        params.setMaxCpuTime(1000);
        params.setMaxRealTime(2000);
        params.setMaxCpuTime(128 * 1024 * 1024);
        params.setMaxProcessNumber(200);
        params.setMaxOutputSize(32 * 1024 * 1024);

        params.setExePath("/bin/echo");
        params.setInputPath("/dev/null");
        params.setOutputPath("echo.out");
        params.setErrorPath("echo.out");
        params.setArgs(new String[]{"Hello world"});
        params.setLogPath("judger.log");
        params.setSeccompRuleName("c_cpp");
        params.setEnv(System.getenv().get("PATH").split(";"));
        params.setUid(0);
        params.setGid(0);

        Result run = Executor.exec(params);


        System.out.println(run);
    }
}
