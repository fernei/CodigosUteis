package Model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Ini;
//import org.ini4j.Ini;

public class Banco {

    private String ip;
    private String porta;
    private String banco;
    private String usuario;
    private String senha;

    public Banco() {
        try {
            String pathJar = "Enriquecedor_SAC.jar";
            File file = new File(pathJar);
            String absolutePath = file.getAbsolutePath();
            String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
            String dirLocal = filePath + "\\";
            File conf = new File(dirLocal + "conf.ini");

            if (conf.exists()) {
                Ini ini = new Ini(conf);
                ip = ini.get("BANCO", "IP");
                porta = ini.get("BANCO", "PORTA");
                banco = ini.get("BANCO", "SID");
                usuario = ini.get("BANCO", "USUARIO");
                senha = ini.get("BANCO", "SENHA");
            } else {
                ip = "10.121.241.96";
                porta = "1521";
                banco = "CLIDB";
                usuario = "IVA_SOC";
                senha = "soc123";
            }

        } catch (RuntimeException | IOException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIp() {
        return ip;
    }

    public String getPorta() {
        return porta;
    }

    public String getBanco() {
        return banco;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

}
