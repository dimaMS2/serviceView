package views;

import faces.ConnectParamRow;
import faces.ResultParamRow;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jssc.*;
import math.Converter;
import protocol.Crc16;
import protocol.RussianLetterHelperUM;
import temp.Data;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServiceView extends AnchorPane {
    private String rs = "RS-485";
    private String tcp = "TCP/IP";
    private String connection = "Connection";
    private String graph = "Graph";
    private String params = "Params";

    private String com = "Port";

    private String connect = "Connect";
    private String disconnect = "Disconnect";
    private String clear = "Clear";

    private String baubrateTitle = "Baubrate";
    private String baubrate1 = "9600";
    private String baubrate2 = "19200";
    private String baubrate3 = "36400";
    private String baubrate4 = "57600";
    private String baubrate5 = "115200";
    private String baubrate6 = "230400";

    private String dataBitsTitle = "Data bits";
    private String dataBits1 = "5";
    private String dataBits2 = "6";
    private String dataBits3 = "7";
    private String dataBits4 = "8";

    private String stopBitsTitle = "Stop bits";
    private String stopBits1 = "1";
    private String stopBits2 = "2";

    private String parityTitle = "Parity";
    private String parity0 = "None";
    private String parity1 = "Odd";
    private String parity2 = "Even";

    private String ip = "IP";
    private String ip0 = "192.168.0.1";
    private String port0 = "502";

    private String measTypeTitle = "Measurement type";
    private String sensorTypeTitle = "Sensor type";
    private String sensorTitle = "Sensor";
    private String departmentTitle = "Department";
    private String fieldKeyTitle = "Field Key";
    private String clusterTitle = "cluster";
    private String wellTitle = "Well";
    private String dateTitle = "Date";
    private String stemDiameterTitle = "Stem diameter";
    private String holeTitle = "Hole number";
    private String maxWeightTitle = "Maximum weight";
    private String minWeightTitle = "Minimum weight";
    private String maxRodWeightTitle = "Maximum rod weight";
    private String minRodWeightTitle = "Minimum rod weight";
    private String travelTitle = "Travel";
    private String discrTravelTitle = "Travel discrete";
    private String periodTitle = "Period";
    private String discrTimeTitle = "Time discrete";
    private String cycleTitle = "Cycle";
    private String discrWeightTitle = "WeightDiscrete";


    //        System.out.println("ТИП ИЗВЕРЕНИЯ " + measurementType);
//        System.out.println("ТИП ДАТЧИКА " + sensorType);
//        System.out.println("ДАТЧИК " + sensor);
//        System.out.println("ЦЕХ " + department);
//        System.out.println("МЕСТОРОЖДЕНИЕ " + fieldKey);
//        System.out.println("КУСТ " + cluster);
//        System.out.println("СКВАЖИНА " + well);
//        System.out.println("ДАТА " + strDate);


    private int headerSize = 12;
    private int dataSize = 2122;
    private int crc16Size = 2;
    private int packSize = 2136;
    private int timeOut = 5000;
    private int toastTime = 1000;

    private XYChart.Series<Number, Number> dynamogram;

    private String controlSumBug = "Control sum bug";

    @FXML
    private ChoiceBox<String> connectCB;
    @FXML
    private VBox leftVB, rightVB;
    @FXML
    private TitledPane leftTP, graphTP, rightTP;
    @FXML
    Button connectBtn, disconnectBtn, clearBtn;
    @FXML
    LineChart<Number, Number> chart;

    private SerialPort port;

    public ServiceView(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();

        // temp
        parseData(Data.data);
    }

    private void init() {
        initLeft();
        initCenter();
        initRight();
    }

    private void initLeft() {
        putRs();
        connectCB.getItems().addAll(rs, tcp);
        connectCB.getSelectionModel().selectFirst();
        connectCB.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                disconnectAction();
                leftVB.getChildren().clear();
                if (newValue.equals(0)) {
                    putRs();
                } else if (newValue.equals(1)) {
                    putTcp();
                }
            }
        });

        leftTP.setText(connection);

        connectBtn.setText(connect);
        connectBtn.setOnAction(event -> {
            List<ConnectParamRow> list = new ArrayList<>();
            for (int i = 0; i < leftVB.getChildren().size(); i++) {
                list.add((ConnectParamRow) leftVB.getChildren().get(i));
            }
            if (connectCB.getValue().equals(rs)) {
                String com = list.get(0).getChoiceBox().getValue();
                int baudRate = Integer.parseInt(list.get(1).getChoiceBox().getValue());
                int dataBits = Integer.parseInt(list.get(2).getChoiceBox().getValue());
                int stopBits = Integer.parseInt(list.get(3).getChoiceBox().getValue());
                String lastSelect = list.get(4).getChoiceBox().getValue();
                int parity = 0;
                if (lastSelect.equals(parity1)) {
                    parity = 1;
                } else if (lastSelect.equals(parity2)) {
                    parity = 2;
                }

                createSerialPort(com, baudRate, dataBits, stopBits, parity);

                System.out.println(com + " " + baudRate + " " + dataBits + " " + stopBits + " " + parity);
            } else if (connectCB.getValue().equals(tcp)) {
                // TODO: 30.04.2019
                String ip = list.get(0).getChoiceBox().getValue();
                String port = list.get(1).getChoiceBox().getValue();

                System.out.println(this.ip + " " + ip + " " + this.com + " " + port);
            }
        });

        disconnectBtn.setOnAction(event -> {
            try {
                port.closePort();
            } catch (Exception ignored) {
            }
            port = null;
        });

        clearBtn.setOnAction(event -> {
            disconnectAction();
        });

        disconnectBtn.setText(disconnect);
        clearBtn.setText(clear);
    }

    private void disconnectAction() {
        try {
            System.out.println(Arrays.toString(port.readBytes()));
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    private void putRs() {
        List<String> comItems = Arrays.asList(SerialPortList.getPortNames());
        leftVB.getChildren().add(new ConnectParamRow(com, comItems));
        List<String> baubrateItems = new ArrayList<>(Arrays.asList(baubrate1, baubrate2, baubrate3, baubrate4, baubrate5, baubrate6));
        leftVB.getChildren().add(new ConnectParamRow(baubrateTitle, baubrateItems));
        List<String> dataBitsItems = new ArrayList<>(Arrays.asList(dataBits1, dataBits2, dataBits3, dataBits4));
        leftVB.getChildren().add(new ConnectParamRow(dataBitsTitle, dataBitsItems));
        List<String> stopBitsItems = new ArrayList<>(Arrays.asList(stopBits1, stopBits2));
        leftVB.getChildren().add(new ConnectParamRow(stopBitsTitle, stopBitsItems));
        List<String> parityItems = new ArrayList<>(Arrays.asList(parity0, parity1, parity2));
        leftVB.getChildren().add(new ConnectParamRow(parityTitle, parityItems));
    }

    private void putTcp() {
        List<String> ipItems = Arrays.asList(ip0);
        leftVB.getChildren().add(new ConnectParamRow(ip, ipItems));
        List<String> comItems = Arrays.asList(port0);
        leftVB.getChildren().add(new ConnectParamRow(com, comItems));
    }

    private void createSerialPort(String com, int baudRate, int dataBits, int stopBits, int parity) {
        if (port != null) {
            try {
                port.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }

        port = new SerialPort(com);
        try {
            port.openPort();
            port.setParams(baudRate, dataBits, stopBits, parity);
            port.addEventListener(new MyPortListener());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    private void initCenter() {
        graphTP.setText(graph);
        dynamogram = new XYChart.Series<>();
        chart.getData().add(dynamogram);
        chart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
    }

    private void initRight() {
        rightTP.setText(params);
    }


    class MyPortListener implements SerialPortEventListener {
        public void serialEvent(SerialPortEvent event) {
            try {
                parseData(checkAndGetData());
            } catch (Exception ignored) {
            }
        }

    }

    private void parseData(byte[] data) {
        if (data == null) return;
        // запись типа измерения
        int measurementType = data[44] + data[45] * 256;
        // запись типа датчика
        int sensorType = data[46] + data[47] * 256;
        // запись номера датчика
        int sensor = data[48] + data[49] * 256 + data[50] * 256 * 256 + data[51] * 256 * 256 * 256;
        // запись номера цеха
        int department = data[52] + data[53] * 256;
        // запись кода месторождения
        int fieldKey = data[56] + data[57] * 256;
        // запись куста
        String cluster = parseString(new byte[]{data[58], data[59], data[60], data[61], data[62], data[63]});
        // запись скважины
        String well = parseString(new byte[]{data[64], data[64], data[66], data[67], data[68], data[69]});
        // запись даты замера
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, data[70]);
        calendar.set(Calendar.MINUTE, data[71]);
        calendar.set(Calendar.SECOND, data[72]);
        calendar.set(Calendar.DAY_OF_MONTH, data[73]);
        calendar.set(Calendar.MONTH, data[74] - 1);
        calendar.set(Calendar.YEAR, data[75] + 2000);
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String strDate = dateFormat.format(date);
        // запись диаметра штока
        int stemDiameter = data[82] + data[83] * 256;
        // запись номера отверстия
        int hole = data[84] + data[85] * 256;
        // запись максимальной и минимальной нагрузки
        int maxWeight = data[86] + data[87] * 256;
        int minWeight = data[88] + data[89] * 256;
        // запись максимального и минимального веса штанг
        int maxRodWeight = data[90] + data[91] * 256;
        int minRodWeight = data[92] + data[93] * 256;
        // запись хода штока + положение на старте записи
        int travel = data[94] + data[95] * 256;
        // запись дискреты перемещения
        int discrTravel = data[98] + data[99] * 256;
        // запись периода качаний
        int period = data[100] + data[101] * 256;
        // запись дискреты времени
        int discrTime = data[102] + data[103] * 256;
        // запись количества циклов динамограммы
        int cycle = data[104] + data[105] * 256;
        // запись дискреты нагрузки
        int discrWeight = data[106] + data[107] * 256;
        // запись точек динамограммы
        short[] values = new short[1000];
        int first, second, buffer;
        for (int i = 0; i < values.length; i++) {
            first = (data[120 + i * 2] < 0) ? data[120 + i * 2] + 256 : data[120 + i * 2];
            second = (data[120 + i * 2 + 1] < 0) ? data[120 + i * 2 + 1] + 256 : data[120 + i * 2 + 1];
            buffer = ((second << 8) | first);
            if (buffer > 32767) {
                values[i] = (short) (buffer - 65536);
            } else {
                values[i] = (short) buffer;
            }
        }

        double[][] dgm = ddin2DataConverting(values, (short) discrTravel, (short) discrWeight);

        // define zero points
        int zeroPointCount = 0;
        for (int i = dgm.length - 1; i > -1; i--) {
            if (dgm[i][0] == 0.0 && dgm[i][1] == 0.0) zeroPointCount++;
        }

        // update Graph
        dynamogram.getData().clear();
        for (int i = 0; i < dgm.length - zeroPointCount; i++) {
            dynamogram.getData().add(new XYChart.Data<>(dgm[i][0], dgm[i][1]));
        }

        // update Params
        updateParams(measurementType, sensorType, sensor, department, fieldKey, cluster, well, strDate, stemDiameter,
                hole, maxWeight, minWeight, maxRodWeight, minRodWeight, travel, discrTravel, period, discrTime, cycle,
                discrWeight);


//        System.out.println("ТИП ИЗВЕРЕНИЯ " + measurementType);
//        System.out.println("ТИП ДАТЧИКА " + sensorType);
//        System.out.println("ДАТЧИК " + sensor);
//        System.out.println("ЦЕХ " + department);
//        System.out.println("МЕСТОРОЖДЕНИЕ " + fieldKey);
//        System.out.println("КУСТ " + cluster);
//        System.out.println("СКВАЖИНА " + well);
//        System.out.println("ДАТА " + strDate);
    }

    private void updateParams(int measurementType, int sensorType, int sensor, int department, int fieldKey,
                              String cluster, String well, String strDate, int stemDiameter, int hole, int maxWeight,
                              int minWeight, int maxRodWeight, int minRodWeight, int travel, int discrTravel, int period,
                              int discrTime, int cycle, int discrWeight) {
        rightVB.getChildren().clear();
        rightVB.getChildren().add(new ResultParamRow(measTypeTitle, String.valueOf(measurementType), null));
        rightVB.getChildren().add(new ResultParamRow(sensorTypeTitle, String.valueOf(sensorType), null));
        rightVB.getChildren().add(new ResultParamRow(sensorTitle, String.valueOf(sensor), null));
        rightVB.getChildren().add(new ResultParamRow(departmentTitle, String.valueOf(department), null));
        rightVB.getChildren().add(new ResultParamRow(fieldKeyTitle, String.valueOf(fieldKey), null));
        rightVB.getChildren().add(new ResultParamRow(clusterTitle, cluster, null));
        rightVB.getChildren().add(new ResultParamRow(wellTitle, well, null));
        rightVB.getChildren().add(new ResultParamRow(dateTitle, strDate, null));
        rightVB.getChildren().add(new ResultParamRow(stemDiameterTitle, String.valueOf(Converter.mmToCm((double) stemDiameter)), null));
        rightVB.getChildren().add(new ResultParamRow(holeTitle, String.valueOf(hole), null));
        rightVB.getChildren().add(new ResultParamRow(maxWeightTitle, String.valueOf(Converter.weight((double) maxWeight, discrWeight)), null));
        rightVB.getChildren().add(new ResultParamRow(minWeightTitle, String.valueOf(Converter.weight((double) minWeight, discrWeight)), null));
        rightVB.getChildren().add(new ResultParamRow(maxRodWeightTitle, String.valueOf(Converter.weight((double) maxRodWeight, discrWeight)), null));
        rightVB.getChildren().add(new ResultParamRow(minRodWeightTitle, String.valueOf(Converter.weight((double) minRodWeight, discrWeight)), null));
        rightVB.getChildren().add(new ResultParamRow(travelTitle, String.valueOf(Converter.travel((double) travel)), null));
        rightVB.getChildren().add(new ResultParamRow(discrTravelTitle, String.valueOf(discrTravel), null));
        rightVB.getChildren().add(new ResultParamRow(periodTitle, String.valueOf(Converter.period((double) period)), null));
        rightVB.getChildren().add(new ResultParamRow(discrTimeTitle, String.valueOf(discrTime), null));
        rightVB.getChildren().add(new ResultParamRow(cycleTitle, String.valueOf(cycle), null));
        rightVB.getChildren().add(new ResultParamRow(discrWeightTitle, String.valueOf(discrWeight), null));

    }

    private byte[] checkAndGetData() {
        byte[] data = new byte[dataSize];
        byte[] crc16 = new byte[crc16Size];
        try {
            byte[] pack = port.readBytes(packSize, timeOut);

            for (int i = 0; i < dataSize; i++) {
                data[i] = pack[i + headerSize];
            }

            for (int i = 0; i < crc16Size; i++) {
                crc16[i] = pack[i + headerSize + dataSize];
            }

            byte[] calcCrc16 = Crc16.crc16(data, 0, dataSize);

            if (!Arrays.equals(calcCrc16, crc16)) {
                showToast((Stage) getScene().getWindow(), controlSumBug);
                return null;
            }
        } catch (Exception ignored) {
        }

        return data;
    }

    private String parseString(byte[] data) {
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 48) {
                break;
            }
            count++;
        }

        List<Character> chars = new ArrayList<>();
        for (int i = count; i < data.length; i++) {
            chars.add(RussianLetterHelperUM.getRussianLetter(data[i]));
        }

        StringBuilder builder = new StringBuilder(chars.size());
        for (Character ch : chars) {
            builder.append(ch);
        }
        return builder.toString();
    }

    private static double[][] ddin2DataConverting(short[] data, int step, int weight_discr) {
        double[][] array = new double[data.length][2];
        step = ((step < 0) ? step + 65536 : step) / 10;
        weight_discr = (weight_discr < 0) ? weight_discr + 65536 : weight_discr;
        for (int i = 0; i < data.length; i++) {
            if (i == 0) {
                array[i][0] = (getTravelFromSensorData(data[i]) * step) / 1000f;
            } else {
                array[i][0] = (getTravelFromSensorData(data[i]) * step) / 1000f +
                        array[i - 1][0];
            }
            array[i][1] = Math.abs((getWeighFromSensorData(data[i]) * weight_discr) / 1000f);
        }
        double min = Double.MAX_VALUE;
        for (int i = 0; i < data.length; i++) {
            if (min > array[i][0]) {
                min = array[i][0];
            }
        }
        for (int i = 0; i < data.length; i++) {
            array[i][0] = array[i][0] - min;
        }
        return array;
    }

    private static int getWeighFromSensorData(short number) {
        short mask = 1023;
        return number & mask;
    }

    private static int getTravelFromSensorData(short number) {
        int mask = 64512;
        int result = (number & mask) >> 10;
        if (result > 31) {
            result = result - 64;
        }
        return result;
    }

    public void showToast(Stage stage, String message) {
        Stage toastStage = new Stage();
        toastStage.initOwner(stage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(message);
        text.setId("toast-text");

        StackPane pane = new StackPane(text);
        pane.setId("toast-pane");
        pane.setOpacity(0);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("/css/classic.css");
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey = new KeyFrame(Duration.millis(toastTime),
                new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey);
        fadeInTimeline.setOnFinished((event) -> new Thread(() -> {
            try {
                Thread.sleep(toastTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Timeline fadeOutTimeline = new Timeline();
            KeyFrame fadeOutKey = new KeyFrame(Duration.millis(toastTime),
                    new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
            fadeOutTimeline.getKeyFrames().add(fadeOutKey);
            fadeOutTimeline.setOnFinished((event1) -> toastStage.close());
            fadeOutTimeline.play();
        }).start());
        fadeInTimeline.play();
    }

}
