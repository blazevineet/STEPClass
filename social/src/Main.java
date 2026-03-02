public class Main {
    public static void main(String[] args) {
        UsernameService service = new UsernameService();

        service.registerUser("john_doe", 101);

        System.out.println(service.checkAvailability("john_doe")); // false
        System.out.println(service.checkAvailability("jane_smith")); // true

        System.out.println(service.suggestAlternatives("john_doe"));
        System.out.println(service.getMostAttempted());}
}
