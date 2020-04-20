import java.util.*;
import java.io.*;

public class DataProcessor {

    private static Map<String, Integer> cityID = new HashMap<>();
    private static Map<String, Integer> stationID = new HashMap<>();
    private static Map<String, Integer> trainID = new HashMap<>();

    public static void main(String[] args) throws IOException {
        CreateTableCity();
        CreateTableStation();
        CreateTableTrain();
        CreateTableTrainAndStation();
        CreateTableTicketType();
    }

    public static void CreateTableCity() throws IOException {
        File f = new File("./cityProvinceData.csv");
        Scanner in = new Scanner(f);
        BufferedWriter city = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./createTableSource/City.csv")));
        city.write("city_id,city_name,province_name\n");
        String line;
        int count = 1;
        while(in.hasNextLine())
        {
            line = in.nextLine();
            String[] str = line.split(",");
            if(str.length == 1) {
                city.write(count + "," + str[0] + "," + str[0] + "\n");
                cityID.put(str[0], count);
                count++;
            } else {
                String nameProvince = str[0];
                for(int i=1; i<str.length; i++)
                {
                    city.write(count + "," + str[i] + "," + nameProvince + "\n");
                    cityID.put(str[i], count);
                    count++;
                }
            }
        }
        city.close();
        System.out.println("city Count = " + count);
    }

    public static void CreateTableStation() throws IOException {
        File f = new File("./cityStationData.csv");
        Scanner in = new Scanner(f);
        BufferedWriter station = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./createTableSource/Station.csv")));
        String line;
        int count = 1;
        station.write("station_id,station_name,city_id\n");
        while(in.hasNextLine())
        {
            line = in.nextLine();
            String[] str = line.split(",");
            String cityName = str[0];
            int city_id = cityID.get(cityName);
            for(int i=1; i<str.length; i++)
            {
                station.write(count + "," + str[i].trim() + "," + city_id + "\n");
                stationID.put(str[i].trim(), count);
                count++;
            }
        }
        station.close();
        System.out.println("station Count = " + count);
    }

    public static void CreateTableTrain() throws IOException {
        File folder = new File("./trainData");
        BufferedWriter train = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./createTableSource/Train.csv")));
        train.write("train_id,train_code,train_type,start_station,end_station,total_time,total_mile\n");
        int count = 1;
        for(File f : folder.listFiles())
        {
            try{
                String trainCode = f.getName().substring(0, f.getName().length()-4);
                Scanner in = new Scanner(f);
                String line;
                line = in.nextLine();
                String[] basicInfo = line.split("\t");
                String trainType = basicInfo[0].substring(5);
                String totalTime = basicInfo[1].substring(5, basicInfo[1].length()-3);
                String totalMile = basicInfo[2].substring(5, basicInfo[2].length()-3);
                in.nextLine();
                String startInfo = in.nextLine();
                String endInfo = null;
                while(in.hasNextLine()){
                    line = in.nextLine();
                    endInfo = line;
                }
                String[] start = startInfo.split("\t");
                String[] end = endInfo.split("\t");
                int startStation = stationID.get(start[1]);
                int endStation = stationID.get(end[1]);
                train.write(count + "," + trainCode + "," + trainType + "," + startStation + "," + endStation + "," + totalTime + "," + totalMile + "\n");
                trainID.put(trainCode, count);
                count++;
                in.close();
            } catch (Exception e){
//                System.out.println("Error when " + f.getName().substring(0, f.getName().length()-4));
            }
        }
        train.close();
        System.out.println("train Count = " + count);
    }

    public static void CreateTableTrainAndStation() throws IOException {
        File folder = new File("./trainData");
        BufferedWriter train_and_station = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./createTableSource/Train_and_Station.csv")));
        train_and_station.write("train_id,station_id,passing_order,arrival_time,departure_time,total_time,miles,former_station,latter_station\n");
        int count = 1;
        for(File f : folder.listFiles())
        {
            try{
                String trainCode = f.getName().substring(0, f.getName().length()-4);
                int train_id = trainID.get(trainCode);
                Scanner in = new Scanner(f);
                String line;
                in.nextLine();
                in.nextLine();
                ArrayList<trainAndStation> arr = new ArrayList<>();
                ArrayList<Integer> former = new ArrayList<>();
                ArrayList<Integer> latter = new ArrayList<>();
                while(in.hasNextLine())
                {
                    line = in.nextLine();
                    trainAndStation temp = new trainAndStation();
                    temp.train_id = train_id;
                    String[] str = line.split("\t");
                    temp.passing_order = str[0];
                    String stationName = str[1];
                    int station_id = stationID.get(stationName);
                    temp.station_id = station_id;
                    former.add(station_id);
                    latter.add(station_id);
                    temp.arrival_time = str[2];
                    temp.departure_time = str[3];
                    temp.total_time = str[4];
                    temp.miles = str[5];
                    arr.add(temp);
                }
                arr.get(0).former = -1;
                for(int i=1; i<arr.size(); i++){
                    arr.get(i).former = former.get(i-1);
                }
                arr.get(arr.size()-1).latter = -1;
                for(int i=0; i<arr.size()-1; i++){
                    arr.get(i).latter = latter.get(i+1);
                }
                trainAndStation temp;
                for(int i=0; i<arr.size(); i++){
                    temp = arr.get(i);
                    if(temp.former == -1) {
                        train_and_station.write(temp.train_id + "," + temp.station_id + "," + temp.passing_order + "," + temp.arrival_time + "," + temp.departure_time + "," + temp.total_time + "," + temp.miles + "," + "," + temp.latter + "\n");
                    } else if(temp.latter == -1) {
                        train_and_station.write(temp.train_id + "," + temp.station_id + "," + temp.passing_order + "," + temp.arrival_time + "," + temp.departure_time + "," + temp.total_time + "," + temp.miles + "," + temp.former + "," + "\n");
                    } else {
                        train_and_station.write(temp.train_id + "," + temp.station_id + "," + temp.passing_order + "," + temp.arrival_time + "," + temp.departure_time + "," + temp.total_time + "," + temp.miles + "," + temp.former + "," + temp.latter + "\n");
                    }
                }

                count++;
                in.close();
            } catch (Exception e){
//                System.out.println("Error when " + f.getName().substring(0, f.getName().length()-4));
            }
        }
        train_and_station.close();
        System.out.println("train_and_station Count = " + count);
    }

    public static void CreateTableTicketType() throws IOException {
        File folder = new File("./trainData");
        BufferedWriter ticket_type = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./createTableSource/Ticket_Type.csv")));
        ticket_type.write("ticket_type_id,train_id,dates,seat_type,depart_station,arrive_station,rest_num,price\n");
        int count = 1;
        for(File f : folder.listFiles())
        {
            try{
                String trainCode = f.getName().substring(0, f.getName().length()-4);
                int train_id = trainID.get(trainCode);
                Scanner in = new Scanner(f);
                String line;
                in.nextLine();
                in.nextLine();
                ticket temp = new ticket();
                while(in.hasNextLine())
                {
                    line = in.nextLine();
                    temp.train_id = train_id;
                    String[] str = line.split("\t");
                    String passing_order = str[0];
                    String stationName = str[1];
                    int station_id = stationID.get(stationName);
                    temp.pass.add(station_id);
                    String arrival_time = str[2];
                    String departure_time = str[3];
                    String total_time = str[4];
                    String miles = str[5];
                    if(miles.equals("-")){
                        temp.mile.add(0);
                    } else {
                        temp.mile.add(Integer.parseInt(miles));
                    }
                }
                int year = 2020;
                int month = 1;
                int day = 1;
                String date = year + "-" + month + "-" + day;
                String[] seatType = {"business_class", "first_class", "second_class"};
                int[] restNum = {50, 100, 500};
                double[] multi = {3, 1.5, 1};
                double basicPrice = 0.46;
                for(int i=0; i<temp.pass.size(); i++)
                {
                    for(int j=i+1; j<temp.pass.size(); j++)
                    {
                        double km = temp.mile.get(j) - temp.mile.get(i);
                        double secondPrice = basicPrice * km;
                        if(secondPrice <= 0){
                            secondPrice = 0;
                        }
                        for(int k=0; k<seatType.length; k++)
                        {
                            double price = secondPrice * multi[k];
                            String result = String.format("%.2f", price);
                            price = Double.parseDouble(result);
                            ticket_type.write(count + "," + temp.train_id + "," + date + "," + seatType[k] + "," + temp.pass.get(i) + "," + temp.pass.get(j) + "," + restNum[k] + "," + price + "\n");
                            count++;
                        }
                    }
                }
                in.close();
            } catch (Exception e){
//                System.out.println("Error when " + f.getName().substring(0, f.getName().length()-4));
            }
        }
        ticket_type.close();
        System.out.println("ticket_type Count = " + count);
    }

    public static void mergeAndRemovePostfix() throws IOException {
        BufferedWriter cityStationAll = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./otherData/cityStationDataAll.csv")));
        for(int i=1; i<=5; i++)
        {
            File f = new File("./otherData/cityStationData" + i + ".csv");
            Scanner in = new Scanner(f);
            String line;
            while (in.hasNextLine())
            {
                line = in.nextLine();
                cityStationAll.write(line + "\n");
            }
            in.close();
        }
        cityStationAll.close();

        BufferedWriter cityStation = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./cityStationData.csv")));
        File f = new File("./otherData/cityStationDataAll.csv");
        Scanner in = new Scanner(f);
        String line;
        while (in.hasNextLine())
        {
            line = in.nextLine();
            String[] name = line.split(",");
            String cityName = name[0];
            StringBuilder sb = new StringBuilder();
            sb.append(cityName);
            for(int i=1; i<name.length; i++)
            {
                String str = name[i].substring(0, name[i].length()-3);
                sb.append(",").append(str);
            }
            cityStation.write(sb.toString() + " \n");
        }
        cityStation.close();
    }

}

class trainAndStation{
    int train_id;
    int station_id;
    String passing_order;
    String arrival_time;
    String departure_time;
    String total_time;
    String miles;
    int former;
    int latter;
}

class ticket{
    int train_id;
    ArrayList<Integer> pass = new ArrayList<>();
    ArrayList<Integer> mile = new ArrayList<>();
}
