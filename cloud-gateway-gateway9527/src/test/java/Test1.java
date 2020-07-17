import java.time.ZonedDateTime;

public class Test1 {

    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        System.out.println(zbj);
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("now = " + now);
    }
}
