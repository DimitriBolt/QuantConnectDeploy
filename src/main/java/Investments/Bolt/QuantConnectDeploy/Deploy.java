package Investments.Bolt.QuantConnectDeploy;

import com.jcraft.jsch.*;

import java.io.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class Deploy {
    boolean is_toFTP_ok;
    boolean is_Deploy_ok;
    boolean is_toCSV_ok;
    private NexTradingDay nexTradingDay;


    public static void main(String args[]) {
        // tickerChooser хранит в себе список tickers.
        // https://www.nasdaq.com/market-activity/stocks/screener
        File iFile;
        if (args.length != 0) {
            iFile = new File(args[0]);
        } else {
            String userHomePath = System.getProperty("user.home");
            iFile = new File(userHomePath, "/Downloads/pricing/ifile.txt");
        }

        NexTradingDay nexTradingDay = new NexTradingDay(LocalDate.now(), 1);

        ActualPrediction actualPrediction = new ActualPrediction(nexTradingDay);

        ActualPortfolio actualPortfolio = new ActualPortfolio(iFile, actualPrediction, nexTradingDay);
        boolean is_toCSV_ok = to_csv(actualPortfolio, nexTradingDay);
//        boolean is_toFTP_ok = to_ftp(actualPortfolio, nexTradingDay);
        int i = 1;


    }

    private static boolean to_csv(ActualPortfolio actualPortfolio, NexTradingDay nexTradingDay) {
        String csv = "ticker\t" + ZonedDateTime.now() + "\n" + actualPortfolio.getActualPortfolio().toString().replace("{", "").replace("}", "").replace(", ", "\n").replace("=", "\t");
        try (FileOutputStream out = new FileOutputStream("/home/dimitri/Documents/Forecasts/portfolio-" + nexTradingDay.getNextTradingDay() + ".csv");
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            // перевод строки в байты
            byte[] buffer = csv.getBytes();
            bos.write(buffer, 0, buffer.length);
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        return false;
    }

    static boolean to_ftp(ActualPortfolio actualPortfolio, NexTradingDay nexTradingDay) {
        // https://russianblogs.com/article/1235617302/
        //https://www.baeldung.com/java-file-sftp


        ChannelSftp channelSftp = null;
        try {
            channelSftp = setupJsch();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        try {
            channelSftp.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }

        String localFile = "/home/dimitri/Documents/Forecasts/portfolio-" + nexTradingDay.getNextTradingDay() + ".csv";
        String remoteDir = "/home/ftp_user/Downloads/pricing/";

        try {
            channelSftp.put(localFile, remoteDir + "portfolio-" + nexTradingDay.getNextTradingDay() + ".csv");
        } catch (SftpException e) {
            e.printStackTrace();
        }

        channelSftp.exit();

        return false;
    }

    static private ChannelSftp setupJsch() throws JSchException {
        String remoteHost = "13.83.5.169";
        String username = "ftp_user";
        String password = "ftp_password";

        JSch jsch = new JSch();


        try {
            jsch.setKnownHosts("/home/dimitri/.ssh/known_hosts");
        } catch (JSchException e) {
            e.printStackTrace();
        }


        Session jschSession = null;
        try {
            jschSession = jsch.getSession(username, remoteHost);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        jschSession.setPassword(password);

//        java.util.Properties config = new java.util.Properties();
//        config.put("StrictHostKeyChecking", "no");
//        jschSession.setConfig(config);

//        jsch.setKnownHosts("~/.ssh/known_hosts");
//        String knownHostPublicKey = "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIGiU/mDihXQFoq/wn4isZj5HnrdcoON1NczMbNh6z25o dimitri@carmot-neuro";
//        jschSession.setKnownHosts(new ByteArrayInputStream(knownHostPublicKey.getBytes()));

        jschSession.setConfig("StrictHostKeyChecking", "no");

        try {
            jschSession.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }

        return (ChannelSftp) jschSession.openChannel("sftp");
    }


}
