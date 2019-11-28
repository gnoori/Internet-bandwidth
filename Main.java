import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main{
    public static void main(String[] args) throws Exception {
        //size of the File
        int size;
        //System.out.println("Please enter the URL:");
        //Scanner class to accept input from the USER
        URL url = new URL( " " + args[0]);
        //Make a URL connection
        URLConnection conn = url.openConnection();

        //Getting the size of the file in Bytes and dividing by 1024 to convert into KiloBytes
        size = conn.getContentLength()/1024;
        //Case when file is not accessible or can not determine the file size
        if (size < 0)
            System.out.println("Could not determine file size.");
        //Output the file size
        else
            System.out.println("The size of file is: " + size + " KiloBytes");
        conn.getInputStream().close();



        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        File outputFile = new File("output.jpg"); //The output file
        FileOutputStream fos = new FileOutputStream(outputFile);



        outputFile.createNewFile();
        //start time
        long startTime = System.nanoTime();
        System.out.println("Downloading start time: " + startTime);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        //end time of download
        long endTime = System.nanoTime(); //Measure when we're done downloading the file
        System.out.println("Downloading end time: " + endTime);

        //total time it took to download the file
        long elapsedTime = endTime-startTime;
        System.out.println("Downloading duration in nanoseconds: "+ elapsedTime + " nanoseconds");

        //convert nanoseconds into seconds
        double inSeconds = (double)elapsedTime/1000000000;
        System.out.println("Downloading duration in seconds is: " + inSeconds + " seconds");

        long convert = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        //System.out.println(convert);

        System.out.println(size/convert + " Kb/s");
        File file = new File("output.jpg");
        file.delete();

    }
}
