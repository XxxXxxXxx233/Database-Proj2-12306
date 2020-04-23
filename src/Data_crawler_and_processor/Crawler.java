package Data_crawler_and_processor;

import java.util.*;
import java.io.*;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36";

    public static void main(String[] args) throws IOException, InterruptedException {
//        stationDataCrawler();
//        trainDataCrawler();
//        cityStationCrawler();
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
        String stationDataFolder = "./stationData";
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
        String trainDataFolder = "./trainData";
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

    public static void cityStationCrawler() throws IOException, InterruptedException {
        String web = "http://huochezhan.114piaowu.com";

        Document doc = Jsoup
                .connect(web)
                .userAgent(userAgent)
                .get();
        Element content = doc.getElementById("content");
        Elements city = content.select("span").select("a");
        System.out.println(city.text());
        BufferedWriter cityStation = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./cityStationData.csv")));
        for(int i=0; i<city.size(); i++)
        {
            try{
                String nameCity = city.get(i).text();
                String href_to_each_city = city.get(i).attr("href");
                Document docCity = Jsoup
                        .connect(href_to_each_city)
                        .userAgent(userAgent)
                        .referrer(web)
                        .get();
                Elements stationContent = docCity.getElementsByClass("train_list");
                Elements nameStation = stationContent.select("li").select("dl").select("dt").select("a");
                StringBuilder sb = new StringBuilder();
                sb.append(nameCity);
                for(int j=0; j<nameStation.size(); j++){
                    sb.append("," + nameStation.get(j).text());
                }
                sb.append("\n");
                cityStation.write(sb.toString());
                System.out.println(nameCity);
                Thread.sleep((int) (500 * Math.random()));
            } catch (HttpStatusException e) {
                System.out.println("404 when i = " + i);
                break;
            }
        }
        cityStation.close();
    }

    public static void cityProvinceDataCrawler() throws IOException, InterruptedException {
        String web = "http://huochezhan.114piaowu.com";

        Document doc = Jsoup
                .connect(web)
                .userAgent(userAgent)
                .get();
        Element content = doc.getElementById("content");
        Elements all = content.select("dd");
        BufferedWriter cityProvince = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./cityProvinceData.csv")));
        for(int i=0; i<all.size(); i++){
            Elements city = all.get(i).select("a");
            if(i == 0) {
                String[] str = city.text().split(" ");
                for(int j=0; j<str.length; j++)
                {
                    cityProvince.write(str[j] + "\n");
                }
            } else {
                StringBuilder sb = new StringBuilder();
                String province = city.get(0).text();
                sb.append(province.substring(0, province.length()-3));
                for(int j=1; j<city.size(); j++)
                {
                    String nameCity = city.get(j).text();
                    sb.append(",").append(nameCity);
                }
                cityProvince.write(sb.toString() + "\n");
            }
        }
        cityProvince.close();
    }

}
