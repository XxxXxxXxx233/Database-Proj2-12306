import java.util.*;
import java.io.*;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawler {

    private static String stationDataFolder = "./stationData";
    private static String trainDataFolder = "./trainData";
    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36";

    public static void main(String[] args) throws IOException, InterruptedException {
//        stationDataCrawler();
//        trainDataCrawler();
//        cityProvinceDataCrawler();
    }

    public static void stationDataCrawler() throws IOException, InterruptedException {
        String rootPage = "https://qq.ip138.com";
        String web = "https://qq.ip138.com/train/";
        Document docProvince = Jsoup
                .connect(web)
                .referrer(web)
                .userAgent(userAgent)
                .get();
        Elements province = docProvince.getElementsByClass("table").select("a");
        File data = new File(stationDataFolder);
        if(!data.exists())
            data.mkdir();
        for(int i=0; i<province.size(); i++) {
            try {
                String nameProvince = province.get(i).text();
                String href_to_station = province.get(i).attr("href");
                File provinceFolder = new File(stationDataFolder + "/" + nameProvince);
                if (!provinceFolder.exists())
                    provinceFolder.mkdir();
                Document docStation = Jsoup
                        .connect(rootPage + href_to_station)
                        .referrer(web)
                        .userAgent(userAgent)
                        .get();
                Elements station = docStation.getElementsByClass("table").select("a");
                for (int j = 0; j < station.size(); j++) {
                    try {
                        String nameStation = station.get(j).text();
                        String href_to_train = station.get(j).attr("href");
                        BufferedWriter stationInfo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(provinceFolder.getPath() + "/" + nameStation + ".txt")));
                        Document docTrainInStation = Jsoup
                                .connect(rootPage + href_to_train)
                                .referrer(rootPage + href_to_station)
                                .userAgent(userAgent)
                                .get();
                        Elements trainInStation = docTrainInStation.getElementsByClass("table");
                        Elements trainTable = trainInStation.get(0).select("tr");
                        for (int k = 1; k < trainTable.size(); k++) {
                            Elements eachCol = trainTable.get(k).select("td");
                            StringBuilder sb = new StringBuilder();
                            for (int t = 0; t < eachCol.size(); t++) {
                                sb.append(eachCol.get(t).text() + " ");
                            }
                            stationInfo.write(sb.toString());
                            stationInfo.write("\n");
                        }
                        stationInfo.flush();
                        stationInfo.close();
                        System.out.println("Info of " + nameStation + " in " + nameProvince + " is collected!");
                    } catch (HttpStatusException e) {
                        System.out.println("Station 502 Continue");
                    }
                }
                System.out.println("Info of province " + nameProvince + " is collected!");
                Thread.sleep((int) (500 * Math.random()));
            } catch (HttpStatusException e) {
                System.out.println("Province 502 Continue");
            }
        }
    }

    public static void trainDataCrawler() throws IOException, InterruptedException {
        String rootPage = "https://qq.ip138.com";
        String web = "https://qq.ip138.com/train/";
        ArrayList<String> uniqueTrain = new ArrayList<>();
        Document docProvince = Jsoup
                .connect(web)
                .referrer(web)
                .userAgent(userAgent)
                .get();
        Elements province = docProvince.getElementsByClass("table").select("a");
        File data = new File(trainDataFolder);
        if(!data.exists())
            data.mkdir();
        for(int i = 0; i < province.size(); i++)
        {
            try {
                String nameProvince = province.get(i).text();
                String href_to_station = province.get(i).attr("href");
                Document docStation = Jsoup
                        .connect(rootPage + href_to_station)
                        .referrer(web)
                        .userAgent(userAgent)
                        .get();
                Elements station = docStation.getElementsByClass("table").select("a");
                for (int j = 0; j < station.size(); j++)
                {
                    try {
                        String nameStation = station.get(j).text();
                        String href_to_train = station.get(j).attr("href");
                        Document docTrainInStation = Jsoup
                                .connect(rootPage + href_to_train)
                                .referrer(rootPage + href_to_station)
                                .userAgent(userAgent)
                                .get();
                        Elements trainInStation = docTrainInStation.getElementsByClass("table");
                        Elements trainTable = trainInStation.get(0).select("tr");
                        for (int k = 1; k < trainTable.size(); k++)
                        {
                            String trainName = trainTable.get(k).select("td").select("a").text();
                            String href_to_trainDetail = trainTable.get(k).select("td").select("a").attr("href");
                            if(!uniqueTrain.contains(trainName))
                            {
                                uniqueTrain.add(trainName);
                                try
                                {
                                    BufferedWriter trainInfo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(trainDataFolder + "/" + trainName + ".txt")));
                                    Document docTrain = Jsoup
                                            .connect(rootPage + href_to_trainDetail)
                                            .referrer(web)
                                            .userAgent(userAgent)
                                            .get();
                                    Elements passStation = docTrain.getElementsByClass("table");
                                    for(int z = 0; z < passStation.size(); z++)
                                    {
                                        Elements detailTable = passStation.get(z).select("tr");
                                        for(int x = 0; x < detailTable.size(); x++)
                                        {
                                            StringBuilder sb = new StringBuilder();
                                            Elements eachColumn = detailTable.get(x).select("td");
                                            for(int y = 0; y < eachColumn.size(); y++){
                                                sb.append(eachColumn.get(y).text() + "\t");
                                            }
                                            trainInfo.write(sb.toString());
                                            trainInfo.write("\n");
                                        }
                                    }
                                    trainInfo.flush();
                                    trainInfo.close();
                                    System.out.println("Info of " + trainName + " is collected!");
                                } catch (HttpStatusException e) {
                                    System.out.println("Train 502 Continue");
                                }
                            } else {
                                System.out.println(trainName + " is already exist!");
                            }
                        }
                    } catch (HttpStatusException e) {
                        System.out.println("Station 502 Continue");
                    }
                }
                System.out.println("----------Info of province " + nameProvince + " is collected!----------");
                Thread.sleep((int) (500 * Math.random()));
            } catch (HttpStatusException e) {
                System.out.println("Province 502 Continue");
            }
        }
    }

    public static void cityProvinceDataCrawler() throws IOException {
        String web = "http://www.maps7.com/china_province.php";
        Document doc = Jsoup
                .connect(web)
                .userAgent(userAgent)
                .get();
        Elements province = doc.select("h4");
        String allProvince = province.text();
        Elements all = doc.select("a");
        int count = 46;
        BufferedWriter cityProvince = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./cityProvinceData.csv")));
        StringBuilder sb = new StringBuilder();
        for(int i=count; i<all.size(); i++){
            String name = all.get(i).text();
            if(allProvince.indexOf(name) > 0){
                sb.append("\n");
            }
            sb.append(name + " ");
        }
        Scanner in = new Scanner(sb.toString());
        while(in.hasNextLine()){
            String line = in.nextLine();
            line = line.trim();
            line = line.replace(' ', ',');
            cityProvince.write(line + "\n");
        }
        cityProvince.close();
    }

}
