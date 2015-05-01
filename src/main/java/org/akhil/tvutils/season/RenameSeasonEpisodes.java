/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.akhil.tvutils.season;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Acer
 */
public class RenameSeasonEpisodes {

    String tvSeriesName = "Battlestar Galactica";
    String fullSeriesName = "Battlestar.Galactica";
    String abbr = "BG";
    //String tvSeriesName = "Modern Family";
    int seasonNo = 3;
    String root = "A:/TV-Series";
    String tvRoot = root + "/" + tvSeriesName + "/";
    String season = "season " + seasonNo + "/";
    String tvSeason = tvRoot + season;
    String subDir = tvSeason + "subtitles/";
    String subFile = tvSeason + "episodes.txt";
    File rootFile = new File(tvRoot);
    File subDirFile = new File(subDir);
    File tvSeasonFile = new File(tvSeason);
    
    int maxEpisode;

    String separator = "[a-zA-Z]";

    String format = convert(seasonNo) + "x";
    //String format = "Season." + seasonNo + ".Episode.";
    //String format = seasonNo + "X";

    String reqFormat = "S" + convert(seasonNo) + "E";

    //String filePattern = "S" + convert(seasonNo) + "E";
    //String srtPattern = "S" + convert(seasonNo) + "E";
    //String filePattern = "Season " + seasonNo + " Episode ";
    String filePattern = seasonNo + "*";
    //String srtPattern =  seasonNo + ".";
    //String srtPattern =  seasonNo + "x";
    //String srtPattern = "Season " + seasonNo + " Episode ";
    String srtPattern = seasonNo + "*";

    ArrayList<String> origNames = new ArrayList<String>();
    ArrayList<String> newNames = new ArrayList<String>();

    public static void main(String... args) throws FileNotFoundException, IOException {
        RenameSeasonEpisodes fr = new RenameSeasonEpisodes();
        //fr.extractFilenames();
        fr.readFileNames();
        fr.check();
        //fr.renameAll();
        fr.renameByRegex();
    }

    void rename(File f, String newNameString) throws IOException {    
        Path oldPath = Paths.get(f.getAbsolutePath());
        Path newPath = Paths.get(newNameString);
        System.out.println("Rename " + f.getName() + " to " + newNameString);
        Files.move(oldPath, oldPath.resolveSibling(newPath));
    }
    
    public void check() {
        if(newNames.size() != maxEpisode) {
            System.out.println(newNames.size() + " does not equals " + maxEpisode);
            System.exit(-2);
        }
    }
    
    public void readFileNames() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(subFile);
        BufferedReader br = new BufferedReader(fr);
        String filename;
        int season;
        int episode;
        while ((filename = br.readLine()) != null) {
            origNames.add(filename);
            //System.out.println(filename);
            String newName
                    = filename.replaceAll("\\.en.*", "").trim()
                    .replaceAll(".720p.*", "").trim()
                    .replaceAll(".480p.*", "").trim()
                    .replaceAll(".HDTV.*", "").trim()
                    .replaceAll(".Bluray.*", "").trim()
                    .replaceAll("FoV*", "").trim()
                    .replaceAll(".srt.*", "").trim()
                    .replaceAll("-", "")
                    .replaceAll("'", "")
                    .replaceAll("\\[", "")
                    .replaceAll("\\]", "")
                    .replaceAll("\\ +", "\\.")
                    .replaceAll(fullSeriesName, abbr)
                    .replace(format, reqFormat);
            //System.out.println(format);
            //System.out.println(newName);
            boolean matches = newName.matches("(\\d+).(\\d+)");
            Pattern pattern = Pattern.compile("(\\d+)" + separator + "(\\d\\d)");
            Matcher matcher = pattern.matcher(filename);
            //System.out.println(matcher.groupCount());
            while (matcher.find()) {
                //System.out.println(matcher.group() + " " + matcher.group(0) + " " + matcher.group(1) + " " + matcher.group(2));
                season = Integer.parseInt(matcher.group(1));
                episode = Integer.parseInt(matcher.group(2));
                if(season != seasonNo) {
                    System.out.println("Season no. " + season + " of filename " + newName
                    + "does not match with global season no. " +seasonNo);
                    System.exit(-1);
                }
                if(episode > maxEpisode) {
                    maxEpisode = episode;
                }
            }
            newNames.add(newName);
        }
        for (String s : origNames) {
            System.out.println(s);
        }

        for (String s : newNames) {
            System.out.println(s);
        }
    }

    public void renameByRegex() throws IOException {
        String season;
        int episode;

        for (File f : tvSeasonFile.listFiles()) {
            String filename = f.getName();
            //System.out.println(filename);
            boolean matches = f.getName().matches("(\\d+).(\\d+)");
            Pattern pattern = Pattern.compile("(\\d+)" + separator + "(\\d\\d)");
            Matcher matcher = pattern.matcher(filename);
            //System.out.println(matcher.groupCount());
            while (matcher.find()) {
                //System.out.println(matcher.group() + " " + matcher.group(0) + " " + matcher.group(1) + " " + matcher.group(2));
                season = matcher.group(1);
                episode = Integer.parseInt(matcher.group(2));
                if (episode <= newNames.size()) {
                    String episodeName = newNames.get(episode - 1);
                    String ext = filename.substring(filename.lastIndexOf("."));
                    //System.out.println(season + " " + episode + " " +episodeName + " " +ext);
                    //System.out.println("Rename " + f.getName() + " to " + episodeName + ext);
                    rename(f, episodeName + ext);
                    //f.renameTo(new File(episodeName + "." + ext));
                } else {
                    System.out.println("No subtitle for "+f.getName());
                }
            }
        }
    }

    public void renameByMv(String from, String to) {
        String[] command = {"mv", from, to};
        System.out.println("executing : " + Arrays.toString(command));
        execute(command, tvSeason);
    }

    

    public void extractFilenames() {
        //StringBuffer sb = new StringBuffer();
        System.out.println(subDirFile.getAbsolutePath());
        for (File f : subDirFile.listFiles()) {
            String filename = f.getName();
            origNames.add(filename);
            //System.out.println(filename);
            String newName
                    = filename.replaceAll("\\.en.*", "").trim()
                    .replaceAll(".720p.*", "").trim()
                    .replaceAll(".480p.*", "").trim()
                    .replaceAll(".HDTV.*", "").trim()
                    .replaceAll(".Bluray.*", "").trim()
                    .replaceAll("FoV*", "").trim()
                    .replaceAll(".srt.*", "").trim()
                    .replaceAll("-", "")
                    .replaceAll("'", "")
                    .replaceAll("\\[", "")
                    .replaceAll("\\]", "")
                    .replaceAll("\\ +", "\\.")
                    .replaceAll(fullSeriesName, abbr)
                    .replace(format, reqFormat);
            //System.out.println(format);
            //System.out.println(newName);
            newNames.add(newName);
        }
        for (String s : origNames) {
            System.out.println(s);
        }

        for (String s : newNames) {
            System.out.println(s);
        }

    }

    public void renameAll() {
        //for (File f : tvSeasonFile.listFiles()) {
        //System.out.println(f.getAbsolutePath());

        //}
        int i = 1;
        for (String s : newNames) {
        	renameByMv("*" + filePattern + convert(i) + "*.avi", s + ".avi");
        	renameByMv("*" + filePattern + convert(i) + "*.mp4", s + ".mp4");
        	renameByMv("*" + filePattern + convert(i) + "*.mkv", s + ".mkv");
        	renameByMv("*" + srtPattern + convert(i) + "*.srt", s + ".srt");
            i++;
        }

    }

    public String convert(int i) {
        if (i <= 9) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    public void execute(String[] command, String dir) {

        ProcessBuilder probuilder = new ProcessBuilder(command);
        //You can set up your work directory
        probuilder.directory(new File(dir));

        Process process = null;
        try {
            process = probuilder.start();

            //Read out dir output
            InputStream is = process.getInputStream();
            InputStream er = process.getErrorStream();
            InputStreamReader isr = new InputStreamReader(is);
            InputStreamReader err = new InputStreamReader(er);
            BufferedReader br = new BufferedReader(isr);
            BufferedReader brr = new BufferedReader(err);
            String line;
            System.out.printf("Output of running %s is:\n",
                    Arrays.toString(command));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = brr.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(RenameSeasonEpisodes.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Wait to get exit value
        try {
            int exitValue = process.waitFor();
            System.out.println("\n\nExit Value is " + exitValue);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
